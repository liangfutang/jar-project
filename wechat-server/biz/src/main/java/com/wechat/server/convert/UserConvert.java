package com.wechat.server.convert;

import com.wechat.server.model.UserDO;
import com.wechat.server.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserDTO convert(UserDO user);

    UserDO convert(UserDTO user);

    List<UserDTO> convert(List<UserDO> list);
}
