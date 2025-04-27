package com.wechat.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wechat.server.model.UserDO;
import com.wechat.server.param.UserParam;

import java.util.List;

public interface UserMapper extends BaseMapper<UserDO> {

    List<UserDO> selectListCondition(UserParam param);
}
