package com.ibm.intest.service.impl;

import com.ibm.intest.models.entities.User;
import com.ibm.intest.repositories.UserRepository;
import com.ibm.intest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }
}
