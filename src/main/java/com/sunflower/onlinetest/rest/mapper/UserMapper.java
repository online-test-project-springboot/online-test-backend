package com.sunflower.onlinetest.rest.mapper;

import com.sunflower.onlinetest.entity.UserEntity;
import com.sunflower.onlinetest.rest.response.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserResponse(UserEntity userEntity);
}
