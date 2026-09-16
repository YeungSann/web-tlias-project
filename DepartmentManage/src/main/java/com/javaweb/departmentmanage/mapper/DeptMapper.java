package com.javaweb.departmentmanage.mapper;


import com.javaweb.departmentmanage.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 部署Mapperインターフェース
@Mapper
public interface DeptMapper {
    /*
    // 手動結果マッピング：Deptクラスの createTime および updateTime フィールドをデータベーステーブルの create_time および update_time カラムにマッピング
    @Results({
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
     */

    // SQL文内でキャメルケース命名のフィールドに対してエイリアス（別名）を付与してマッピング
    // 推奨される最適な対応策：yml設定ファイルでキャメルケース自動マッピング（map-underscore-to-camel-case）を有効化すること
    // 全部署データの取得
    @Select("select id, name, create_time, update_time from dept order by update_time desc")
    List<Dept> findAll();


    // 部署の削除
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    // 部署の新規登録
    // プレコンパイルステートメント内には、エンティティクラスで定義されたプロパティ名を指定する必要がある
    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    void addDept(Dept dept);

    // IDによる部署データの取得
    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept findById(Integer id);

    // 部署データの更新
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void updateDept(Dept dept);
}
