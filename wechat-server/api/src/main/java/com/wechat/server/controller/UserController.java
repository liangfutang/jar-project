package com.wechat.server.controller;

import com.projects.common.result.Result;
import com.projects.common.result.Results;
import com.projects.common.utils.ParamValidateUtil;
import com.wechat.server.model.dto.UserDTO;
import com.wechat.server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 新增一个用户
     * @param user 新增用户信息
     * @return 新增结果
     */
    @PostMapping("/save")
    public Result<?> insert(@RequestBody UserDTO user) {
        ParamValidateUtil.notBlank(user.getNickName(), "用户名不能为空");
        ParamValidateUtil.notBlank(user.getPassword(), "密码不能为空");
        return Results.success(userService.insert(user));
    }

}
