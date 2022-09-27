package com.ibm.intest.service;


import com.ibm.intest.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> getAllUsers();

    public Optional<User> getUserById(Long id);
}
