package com.ibm.intest.service;


import com.ibm.intest.dto.UserDto;
import com.ibm.intest.models.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<UserDto> getAllUsers();

    public UserDto getUserById(Long id);

    public Boolean deleteUserById(Long id);

    void saveUser(User newUser);
}
