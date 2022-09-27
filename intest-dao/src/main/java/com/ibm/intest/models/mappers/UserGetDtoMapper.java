package com.ibm.intest.models.mappers;

import com.ibm.intest.dto.UserGetDto;
import com.ibm.intest.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserGetDtoMapper {

    @Mapping(source = "userId", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "age", target = "age")
    UserGetDto toUserGetDto(User user);

    List<UserGetDto> toUsergetDtoList(List<User> users);

}
