package com.wechat.server.controller;

import com.edu.common.enums.AppIdEnum;
import com.edu.common.result.Result;
import com.edu.common.result.Results;
import com.edu.common.utils.HttpRequestUtil;
import com.edu.common.utils.ParamValidateUtil;
import com.edu.platform.auth.LoginService;
import com.wechat.server.model.param.UserApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/simple")
    public Result<?> login(@RequestBody UserApiParam user) {
        ParamValidateUtil.notBlank(user.getAccount(), "账号不能为空");
        ParamValidateUtil.notBlank(user.getPassword(), "密码不能为空");
        AppIdEnum.validValue(HttpRequestUtil.getAppId());
        return Results.success(loginService.login(user));
    }
}
