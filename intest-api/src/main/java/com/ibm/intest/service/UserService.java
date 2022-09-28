package com.ibm.intest.service;


import com.ibm.intest.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public List<UserDto> getAllUsers();

    public UserDto getUserById(Long id);

    public Boolean deleteUserById(Long id);

    void saveUser(UserDto newUser);

    public List<UserDto> getAllUsersWithAge(int age, Pageable pageable);

    public List<UserDto> getAllUsersFirstNameOverGivenAge(String name, int age);
}
