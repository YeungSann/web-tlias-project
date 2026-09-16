package com.javaweb.departmentmanage.service.impl;

import com.javaweb.departmentmanage.mapper.EmpMapper;
import com.javaweb.departmentmanage.mapper.StudentMapper;
import com.javaweb.departmentmanage.pojo.ClazzCountOption;
import com.javaweb.departmentmanage.pojo.JobOption;
import com.javaweb.departmentmanage.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    // 最下層の EmpMapper をインジェクション
    @Autowired
    private EmpMapper empMapper;

    // 受講生（Student）の Mapper 層をインジェクション
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption empJobData() {
        // 最下層の Mapper メソッドを呼び出し、取得した List オブジェクトを展開して key と value をそれぞれ JobOption の jobList、dataList プロパティに格納
        JobOption jobOption = new JobOption();
        List<Map<String,Object>> list = empMapper.countJobData();
        // まず List の非空判定を行い、空でない場合にループ処理で jobList と dataList にそれぞれ格納
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(map->{
                Object position = map.get("position");
                Object quantity = map.get("quantity");

                // jobList と dataList にそれぞれ追加
                jobOption.getJobList().add(position.toString());
                jobOption.getDataList().add(quantity);

            });
        }

        return jobOption;
    }

    @Override
    public List<Map<String,Object>> empGenderData() {
        // 最下層の Mapper メソッドを呼び出し、Map オブジェクトをカプセル化した List を返却
        return empMapper.countGenderData();
    }

    @Override
    public List<Map<String, Object>> studentDegreeData() {
        // 最下層の Mapper メソッドを呼び出し、Map オブジェクトをカプセル化した List を返却
        return studentMapper.countDegreeData();
    }

    @Override
    public ClazzCountOption studentCountData() {
        // studentMapper の countCountData メソッドを呼び出し
        List<Map<String,Object>> list =  studentMapper.countCountData();
        // クラスごとの人数集計結果を格納するための ClazzCountOption オブジェクトを新規作成
        ClazzCountOption clazzCountOption = new ClazzCountOption();

        // まず List の非空判定を実施
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(map->{
                Object clazzName = map.get("clazzName");
                Object quantity = map.get("quantity");

                // clazzList と dataList にそれぞれ追加
                clazzCountOption.getClazzList().add(clazzName.toString());
                clazzCountOption.getDataList().add(quantity);

            });
        }
        return clazzCountOption;
    }
}
