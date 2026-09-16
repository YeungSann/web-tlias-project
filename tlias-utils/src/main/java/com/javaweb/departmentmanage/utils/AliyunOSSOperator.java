package com.javaweb.departmentmanage.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

// 本クラスをIoCコンテナ（Spring Bean）に登録する
@Component
public class AliyunOSSOperator {

    // ymlファイルの配置値を保持するプロパティクラスをインジェクション（注入）する
    @Autowired
    private AliyunOSSproperties aliyunOSSproperties;

    // コンストラクタ
    public AliyunOSSOperator(AliyunOSSproperties aliyunOSSproperties) {

    }

    // ネットワーク経由でアップロードされたファイルを受け取るため、引数にはフォームのMultipartFileオブジェクトを指定する
    public String upload(MultipartFile file) throws Exception {
        // ymlファイルから設定値（エンドポイント、バケット名、リージョン）を取得する
        String endpoint = aliyunOSSproperties.getEndpoint();
        String bucketName = aliyunOSSproperties.getBucketName();
        String region =  aliyunOSSproperties.getRegion();

        // 環境変数からアクセス資格情報（Access Key ID / Secret）を取得する。
        // ※本コードを実行する前に、環境変数 OSS_ACCESS_KEY_ID および OSS_ACCESS_KEY_SECRET が設定されていることを確認すること。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // アップロードされたファイルの元ファイル名（オリジナルファイル名）を取得する
        String originalFileName = file.getOriginalFilename();

        // 現在のシステム日付を取得し、"yyyy/MM" 形式の文字列にフォーマットする（保存先ディレクトリ名として使用）
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        // UUIDを生成してユニークな（一意の）ファイル名を作成し、元ファイルの拡張子を結合する
        String fileName = UUID.randomUUID() + originalFileName.substring(originalFileName.lastIndexOf("."));
        // ディレクトリパスとファイル名を結合し、OSS上のオブジェクト名（パス形式）を生成する
        String objectName = dir + "/" + fileName;

        // OSSClientインスタンスを生成する。
        // ※OSSClientインスタンスが不要になった後は、リソースを解放するために shutdown() メソッドを呼び出すこと。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();

        try {
            // PutObjectRequestオブジェクトを生成（バケット名、オブジェクト名、入力ストリームを設定）
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file.getInputStream());
            // ※アップロード時にストレージタイプやアクセス権限（ACL）を設定する場合は、以下のサンプルコードを参照すること。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // OSSへファイルをアップロードする
            ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            // OSS側でリクエストが拒否された（エラーレスポンスが返された）場合の例外处理
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            // ネットワーク通信不可など、クライアント側で重度な内部エラーが発生した場合の例外处理
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                // OSSクライアントのリソースを解放する
                ossClient.shutdown();
            }
        }
        // アップロード完了後、アクセス可能なURL（https://bucketName.endpoint/objectName 等）を組み立てて返却する
        return endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
    }

}
