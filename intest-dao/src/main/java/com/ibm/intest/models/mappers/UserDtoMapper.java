package com.ibm.intest.models.mappers;

import com.ibm.intest.dto.UserDto;
import com.ibm.intest.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(source = "userId", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "age", target = "age")
    UserDto toUserDto(User user);

    default Optional<User> wrapOptional(User user){
        return Optional.of(user);
    }

    default Optional<UserDto> wrapOptionalDto(UserDto userDto){
        return Optional.of(userDto);
    }


    List<UserDto> toUserDtoList(List<User> users);


    @Mapping(source = "id", target = "userId")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "age", target = "age")
    User UserDtoToUser(UserDto userDto);


    List<User> UserDtoListToUserList(List<UserDto> userDtoList);

}
