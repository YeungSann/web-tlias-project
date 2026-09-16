package com.javaweb.departmentmanage.mapper;

import com.javaweb.departmentmanage.pojo.Student;
import com.javaweb.departmentmanage.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    // 総レコード数の取得
    long getAllLines(@Param("param") StudentQueryParam param);

    // ページング検索
    // 取得対象フィールド：氏名、学籍番号、所属クラス、性別、携帯番号、最終学歴、規律違反回数、減点数、最終更新日時、操作
    // Service層で算出された開始位置（start）をMapper層へ渡してクエリを実行
    List<Student> page(@Param("param") StudentQueryParam param,
                       @Param("start") Integer start,
                       @Param("pageSize") Integer pageSize);

    // IDリスト（ids）による受講生の一括削除
    void deleteById(@Param("ids") List<Integer> ids);

    // 受講生の追加
    // id, name, no, gender, phone, idCard, isCollege, address, degree, graduationDate, clazzId, violationCount, violationScore
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into student" +
            "(name,no,gender,phone,id_card,is_college,address,degree,graduation_date," +
            "clazz_id,create_time,update_time) " +
            "values(#{name},#{no},#{gender},#{phone},#{idCard},#{isCollege}," +
            "#{address},#{degree},#{graduationDate},#{clazzId}," +
            "#{createTime},#{updateTime})")
    void addStudent(Student student);

    // IDによる受講生情報の取得
    @Select("select * from student where id=#{id}")
    Student getById(Integer id);

    // 動的SQLを使用した受講生情報の更新
    void updateStudent(Student student);

    // 受講生の学歴分布の集計---＞戻り値の要素タイプは Map
    List<Map<String, Object>> countDegreeData();

    // クラス別在籍人数の集計---＞戻り値の要素タイプは Map
    List<Map<String, Object>> countCountData();
}
