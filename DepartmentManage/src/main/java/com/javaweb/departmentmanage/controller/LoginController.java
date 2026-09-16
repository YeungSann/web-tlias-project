package com.javaweb.departmentmanage.controller;

import com.javaweb.departmentmanage.pojo.LoginDTO;
import com.javaweb.departmentmanage.pojo.LoginInfo;
import com.javaweb.departmentmanage.pojo.Result;
import com.javaweb.departmentmanage.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LoginController {
    // ログイン処理用コントローラークラス。ログイン関連リクエストの受付・処理を担当する
    // 社員サービス層を注入し、ログイン照会メソッドを呼び出す
    @Autowired
    private EmpService empService;

    // ログイン処理メソッド。POSTリクエスト、リクエストパラメータは username および password（LoginDTO）
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        log.info("社員ログイン: {}", loginDTO.getUsername());
        // 最下位（Service/Mapper）のログイン照会メソッドを呼び出し、LoginInfoにカプセル化してレスポンスする
        LoginInfo loginInfo = empService.login(loginDTO);
        return Result.success(loginInfo);
    }

}
