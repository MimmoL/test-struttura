package com.ibm.intest.service;


import com.ibm.intest.dto.UserDto;
import com.ibm.intest.dto.UserDtoCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    Boolean deleteUserById(Long id);

    void saveUser(UserDto newUser);

    List<UserDto> getAllUsersWithAge(int age, Pageable pageable);

    List<UserDto> getAllUsersFirstNameOverGivenAge(String name, int age);

    List<UserDto> findUsers(UserDtoCriteria criteria);

}
