package com.ibm.intest.models.mappers;

import com.ibm.intest.dto.UserDto;
import com.ibm.intest.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(source = "userId", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "age", target = "age")
    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> users);

}
