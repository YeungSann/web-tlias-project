package com.javaweb.departmentmanage.mapper;

import com.javaweb.departmentmanage.pojo.Clazz;
import com.javaweb.departmentmanage.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClazzMapper {

    // 全クラスの総レコード数を取得

    long findAll(@Param("queryParam") ClazzQueryParam queryParam);

    // 全クラスのページングデータを取得
    // フロントエンド表示項目：clazzテーブル：クラス連番、クラス名、教室、開講日、修了日、ステータス、最終更新日時、操作
    // 2. クラスのページング検索：emp テーブルと結合して担任（マスター）の氏名を取得
    List<Clazz> findPage(@Param("queryParam") ClazzQueryParam queryParam,
                         @Param("start") Integer start,
                         @Param("pageSize") Integer pageSize);

    // IDによるクラスの削除
    @Delete("delete from clazz where id = #{id}")
    void deleteById(Integer id);

    // クラスの追加
    @Insert("insert into clazz " +
            "(name, room, begin_date, end_date, master_id, subject, create_time, update_time) " +
            "values " +
            "(#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})")
    void addClazz(Clazz clazz);

    // IDによるクラス情報の取得。仕様統一・標準化のため、すべてのカラムを明示的に指定
    @Select("select " +
            "id, name, room, begin_date, end_date, master_id, subject, create_time, update_time " +
            "from clazz where id = #{id}")
    Clazz findById(Integer id);


    // クラス情報の更新。更新対象フィールドが多いため、Mapperの動的SQL文を使用して更新処理を実行
    void updateClazz(Clazz clazz);

    // 全クラス一覧の取得
    @Select("select * from clazz")
    List<Clazz> getClazzs();
}
