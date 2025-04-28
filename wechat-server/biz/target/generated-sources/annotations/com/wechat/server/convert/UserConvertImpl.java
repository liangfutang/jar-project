package com.wechat.server.convert;

import com.edu.platform.model.UserDO;
import com.wechat.server.model.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-12T14:29:08+0800",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_351 (Oracle Corporation)"
)
public class UserConvertImpl implements UserConvert {

    @Override
    public UserDTO convert(UserDO user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setName( user.getName() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setMobile( user.getMobile() );
        userDTO.setStates( user.getStates() );
        userDTO.setOperator( user.getOperator() );
        userDTO.setCreateTime( user.getCreateTime() );
        userDTO.setUpdateTime( user.getUpdateTime() );

        return userDTO;
    }

    @Override
    public UserDO convert(UserDTO user) {
        if ( user == null ) {
            return null;
        }

        UserDO userDO = new UserDO();

        userDO.setId( user.getId() );
        userDO.setName( user.getName() );
        userDO.setPassword( user.getPassword() );
        userDO.setMobile( user.getMobile() );
        userDO.setStates( user.getStates() );
        userDO.setOperator( user.getOperator() );
        userDO.setCreateTime( user.getCreateTime() );
        userDO.setUpdateTime( user.getUpdateTime() );

        return userDO;
    }

    @Override
    public List<UserDTO> convert(List<UserDO> list) {
        if ( list == null ) {
            return null;
        }

        List<UserDTO> list1 = new ArrayList<UserDTO>( list.size() );
        for ( UserDO userDO : list ) {
            list1.add( convert( userDO ) );
        }

        return list1;
    }
}
