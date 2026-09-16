package com.javaweb.departmentmanage.mapper;

import com.javaweb.departmentmanage.pojo.Emp;
import com.javaweb.departmentmanage.pojo.EmpQueryParam;
import com.javaweb.departmentmanage.pojo.JobOption;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper  {
    // Mapperインターフェース層：empおよびemp_exprの2つのテーブルに対する操作メソッドを定義

    /*
    // 1. 総レコード数の取得
    @Select("select count(*) from emp")
    long getTotal();

    // 2. 検索結果リストの取得
    // d.name に deptName のエイリアスを付与し、取得した部署名をエンティティにマッピング
    @Select("select e.* ,d.name deptName from emp e left join dept d on " +
            "e.dept_id = d.id order by e.update_time desc limit #{start}, #{pageSize}")
    public List<Emp> findPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);
     */

    // 社員データの取得
    // 自動ページング（PageHelper）を使用するため、開始インデックスおよびページサイズの指定は不要
    // 注意点：PageHelperを使用したページング検索時、SQL文の末尾に「;（セミコロン）」を絶対に入れないこと。
    // PageHelperが自動的にLIMIT句を追加するため、セミコロンがあるとSQL構文エラーが発生する
    /*
    @Select("select e.* ,d.name deptName from emp e left join dept d on " +
            "e.dept_id = d.id order by e.update_time desc")
    public List<Emp> list();


    // 条件付きページング検索メソッド
    // 注意：こちらのSQL文は複雑なため、mapper.xmlマッピングファイルを使用して処理を行う
    //@Select("select e.* ,d.name deptName from emp e left join dept d on " +
    //        "e.dept_id = d.id where e.name like '%#{name}%' and e.gender = #{gender} " +
    //        "and e.entry_date between #{begin} and #{end} order by e.update_time desc ")
    List<Emp> list2(@Param("name") String name, @Param("gender") Integer gender,
                    @Param("begin") LocalDate begin, @Param("end") LocalDate end);
     */

    // 条件付きページング検索---最適化版（オブジェクトラッピング方式）
    List<Emp> list3(EmpQueryParam empQueryParam);


    // 社員新規登録の2つのSQLステートメント---ステップ2の職歴情報一括保存は empExprMapper に定義が必要
    // 1. 社員基本情報の保存
    // @Options アノテーションにより自動生成キー（主鍵）の使用を指定し、生成されたidをオブジェクトに返却
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp (username,name,gender,phone,job, salary, image, entry_date,dept_id,create_time,update_time) values " +
            "(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void saveEmp(Emp emp);


    // 社員情報の削除（IDリストによる一括削除）
    void deleteByIds(@Param("ids")List<Integer> ids);

    // 社員基本情報の取得
    Emp selectById(Integer id);

    // 社員基本情報の更新
    void updateById(Emp emp);

    // 役職別社員数の集計---＞Mapコレクションのリストとしてカプセル化
    List<Map<String,Object>> countJobData();

    // 性別別社員数の集計---＞Mapコレクションのリストとしてカプセル化
    // Mapのキー名を "name" に指定
    //@MapKey("name")
    // resultType に Map を使用した場合、MyBatis内部で List<Map<String, Object>> 形式にカプセル化されるため、そのまま上位層へ返却する
    List<Map<String, Object>> countGenderData();

    // 全担任（job = 1）情報の取得
    @Select("select id, name from emp where job = 1")
    List<Emp> listMaster();

    // 部署IDに基づき、該当部署内に所属社員が存在するか判定（件数取得）
    @Select("select count(*) from emp where dept_id = #{id}")
    Long findByDeptId(Integer id);

    // ユーザー名とパスワードによる社員情報の直接照会（認証用）
    @Select("select * from emp where username = #{username} and password = #{password}")
    Emp selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    // パスワードの更新（IDに基づき）
    int updatePassword(@Param("id") Integer id, @Param("password") String password);
}
