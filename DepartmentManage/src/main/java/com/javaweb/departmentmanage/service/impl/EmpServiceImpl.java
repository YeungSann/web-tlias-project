package com.javaweb.departmentmanage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaweb.departmentmanage.exception.BusinessException;
import com.javaweb.departmentmanage.mapper.EmpExprMapper;
import com.javaweb.departmentmanage.mapper.EmpMapper;
import com.javaweb.departmentmanage.pojo.*;
import com.javaweb.departmentmanage.service.EmpService;
import com.javaweb.departmentmanage.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// EmpのService実装クラス：EmpServiceインターフェースで定義されたメソッドを実装
@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    // empMapperオブジェクトを注入し、empテーブルを操作
    @Autowired
    private EmpMapper empMapper;

    // 職歴情報のMapperオブジェクトを注入
    @Autowired
    private EmpExprMapper empExprMapper;

    /*
    // ページング検索メソッド
    @Override
    public PageResult<Emp> getListPage(Integer page, Integer pageSize) {

        // empMapperの getTotal メソッドを呼び出して総レコード数を取得
        long total = empMapper.getTotal();

        // empMapperの findPage メソッドを呼び出して結果リストを取得
        // 呼び出し前に 開始インデックス = (page - 1) * pageSize により位置を算出
        Integer start = (page - 1) * pageSize;
        List<Emp> rows = empMapper.findPage(start, pageSize);

        // total と list を PageResult オブジェクトにカプセル化して返却
        PageResult<Emp> pageResult = new PageResult<>(total, rows);
        return pageResult;


        // ページングパラメータを設定
        // ここでは現在のページ番号と1ページあたりの件数の2つの引数を渡す
        PageHelper.startPage(page, pageSize);

        // Mapperインターフェースのメソッドを呼び出す
        List<Emp> rows = empMapper.list();
        // 注意：PageHelperは最初のクエリメソッドに対してのみページングを適用し、それ以降のクエリには適用されない
        // つまり、再度 empMapper の findPage メソッドを呼び出してもページング処理は行われない

        // rowsリストを Page オブジェクトにキャストする（PageはListを継承している）
        Page<Emp> pages = (Page<Emp>) rows;

        // 結果を解析・カプセル化して返却
        // 最終的にController層に対して総レコード数と該当ページのデータリストを返却する
        return new PageResult<>(pages.getTotal(), pages.getResult());
    }

    // 条件付きページング検索メソッド
    // Mapperの list2 メソッドを呼び出す
    @Override
    public PageResult<Emp> listPage2(Integer page, Integer pageSize,
                                     String name, Integer gender,
                                     LocalDate begin, LocalDate end) {
        // ページングパラメータを設定
        // 現在のページ番号と1ページあたりの件数を指定
        PageHelper.startPage(page, pageSize);

        // Mapperインターフェースのメソッドを呼び出し、追加された4つの条件パラメータを渡す
        List<Emp> rows = empMapper.list2(name, gender, begin, end);
        // 注意：PageHelperは最初のクエリメソッドに対してのみページングを適用する

        // rowsリストを Page オブジェクトにキャスト
        Page<Emp> pages = (Page<Emp>) rows;

        // 結果を解析・カプセル化して返却
        return new PageResult<>(pages.getTotal(), pages.getResult());
    }
    */

    @Override
    public PageResult<Emp> listPage3(EmpQueryParam empQueryParam) {
        // ページングパラメータを設定
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        // Mapperインターフェースのメソッドを呼び出し
        List<Emp> rows = empMapper.list3(empQueryParam);
        Page<Emp> pages = (Page<Emp>) rows;

        // 結果を解析・カプセル化して返却
        return new PageResult<>(pages.getTotal(), pages.getResult());
    }

    // メソッドにトランザクション（@Transactional）を適用し、内部の2つのデータベース操作が全成功または全失敗（ロールバック）することを保証
    // rollbackFor 属性を追加し、全例外（Exception）でトランザクションがロールバックされるよう指定
    @Transactional(rollbackFor = Exception.class) // トランザクションの開始、コミット、ロールバックを自動制御
    @Override
    public void save(Emp emp)  {
        // empMapperの saveEmp メソッドを呼び出して社員基本情報を保存
        // 呼び出し前に、更新日時を現在日時に設定
        emp.setUpdateTime(LocalDateTime.now());
        // 呼び出し前に、作成日時を現在日時に設定
        emp.setCreateTime(LocalDateTime.now());
        empMapper.saveEmp(emp);

        // 手動で例外を発生させるテストコード
        //if (true){
        //throw new Exception("出错啦~ ~");
        //}

        // まず社員の職歴リスト（exprList）を取得
        List<EmpExpr> exprList = emp.getExprList();
        // 職歴データが存在しない場合と存在する場合があるため、NULL/空判定を実施

        if (!CollectionUtils.isEmpty(exprList)) {
            // 職歴データが存在する場合
            // コレクションをループ処理し、生成された社員ID（emp.getId()）を取得して各 empExpr オブジェクトの empId プロパティに設定
            exprList.forEach(item -> {
                item.setEmpId(emp.getId());
            });

            // empExprMapperの saveBatchExpr メソッドを呼び出し、職歴情報を一括保存（バッチ挿入）
            empExprMapper.saveBatchExpr(exprList);
        }
    }

    // Service内で2つのMapperインターフェースを呼び出しているため、原子性（全成功または全失敗）を保証
    @Transactional(rollbackFor = Exception.class) // トランザクションを自動制御
    @Override
    public void deleteById(List<Integer> ids) {
        // empMapperの deleteByIds メソッドを呼び出し、社員基本情報を削除
        empMapper.deleteByIds(ids);
        // empExprMapperの deleteByEmpIds メソッドを呼び出し、該当社員の職歴（式/関連）情報を削除
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getById(Integer id) {
        // empMapperインターフェースを直接呼び出し、外部結合（OUTER JOIN）により社員基本情報と職歴情報をまとめて取得
        return empMapper.selectById(id);
    }

    // トランザクションを追加し、メソッド内の2つの呼び出しが全成功または全失敗することを保証
    @Transactional(rollbackFor = Exception.class) // トランザクションを自動制御
    @Override
    public void updateById(Emp emp) {
        // まず empMapper の updateById メソッドを呼び出して社員基本情報を更新
        // 更新日時を現在日時に設定
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        // empExprMapper の deleteByEmpIds メソッドを呼び出して該当社員の既存職歴情報を一括削除
        // 既存の deleteByEmpIds メソッドを再利用するため、emp.getId() をリスト構造に変換して渡す
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        // 再度この社員の新しい職歴情報を追加
        List<EmpExpr> exprList = emp.getExprList();
        // 職歴情報が存在するか判定し、存在する場合のみ追加処理を実行
        if (!CollectionUtils.isEmpty(exprList)) {
            // 社員IDを取得して設定
            exprList.forEach(item ->item.setEmpId(emp.getId()));

            // empExprMapper の saveBatchExpr メソッドを呼び出して職歴情報を一括保存
            empExprMapper.saveBatchExpr(exprList);
        }
    }

    @Override
    public List<Emp> listMaster() {
        return empMapper.listMaster();
    }

    @Override
    public LoginInfo login(LoginDTO loginDTO) {
        // 最下層のMapperを呼び出し、該当する社員情報を取得
        Emp emp = empMapper.selectByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

        // ビジネスロジック検証：取得したユーザー情報がNULLの場合、ユーザー名またはパスワードが誤っている
        if (emp == null) {
            // データが取得できない場合（ユーザー名が存在しない、またはパスワード不一致）
            throw new BusinessException("用户名或密码错误");
        }
        // 非NULLの場合、取得したユーザー情報を LoginInfo にカプセル化：id, username, name, token
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId(emp.getId());
        loginInfo.setUsername(emp.getUsername());
        loginInfo.setName(emp.getName());
        // JwtUtils ユーティリティクラスを呼び出し、JWTトークンを生成
        Map<String,Object> claim = new HashMap<>();
        claim.put("id",emp.getId());
        claim.put("username",emp.getUsername());
        loginInfo.setToken(JwtUtils.generateToken(claim));
        // コントローラーへ loginInfo を返却
        return loginInfo;
    }

    @Override
    public void updatePassword(EmpPasswordParam param) {
        // 1. パラメータの検証（ID、新しいパスワード、確認用パスワードの非空チェック）
        if (param == null || param.getId() == null) {
            throw new IllegalArgumentException("ユーザーIDを指定してください。");
        }

        String newPassword = param.getNewPassword();
        String rePassword = param.getRePassword();

        if (!StringUtils.hasText(newPassword) || !StringUtils.hasText(rePassword)) {
            throw new IllegalArgumentException("新しいパスワードおよび確認用パスワードを入力してください。");
        }

        // 2. 新しいパスワードと確認用パスワードの一致チェック
        if (!newPassword.equals(rePassword)) {
            throw new IllegalArgumentException("新しいパスワードと確認用パスワードが一致しません。");
        }

        // 3. 検証通過後、Mapper を呼び出してパスワードを更新
        empMapper.updatePassword(param.getId(), newPassword);
    }


}
