package com.wechat.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.platform.model.UserDO;
import com.edu.platform.param.UserParam;

import java.util.List;

/**
 * 设备 Mapper
 *
 * @author CETC
 */
public interface UserMapper extends BaseMapper<UserDO> {

    List<UserDO> selectListCondition(UserParam param);
}
