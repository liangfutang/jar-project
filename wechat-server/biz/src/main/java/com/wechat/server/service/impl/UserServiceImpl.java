package com.wechat.server.service.impl;

import com.project.framework.common.utils.HttpRequestUtil;
import com.project.framework.redis.facade.RedisFacade;
import com.wechat.server.auth.PasswordEncoder;
import com.wechat.server.convert.UserConvert;
import com.wechat.server.mapper.UserMapper;
import com.wechat.server.model.UserDO;
import com.wechat.server.model.dto.UserDTO;
import com.wechat.server.param.UserParam;
import com.wechat.server.service.UserService;
import com.wechat.server.utils.PhoneCheckUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisFacade redisFacade;

    @Override
    public List<UserDTO> selectByAccount(String account) {
        UserParam param = new UserParam();
        if (PhoneCheckUtils.isPhone(account)) {
            param.setMobile(account);
        } else {
            param.setName(account);
        }
        return UserConvert.INSTANCE.convert(userMapper.selectListCondition(param));
    }

    @Override
    public int insert(UserDTO user) {
        UserDO userDO = UserConvert.INSTANCE.convert(user);
        userDO.setPassword(passwordEncoder.encode(user.getPassword()));
        userDO.setOperator(HttpRequestUtil.getUserName());
        return userMapper.insert(userDO);
    }
}
