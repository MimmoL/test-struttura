package com.ibm.intest.service.impl;

import com.ibm.intest.dto.UserDto;
import com.ibm.intest.models.entities.User;
import com.ibm.intest.models.mappers.UserDtoMapper;
import com.ibm.intest.repositories.UserRepository;
import com.ibm.intest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDtoMapper userDtoMapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userDtoMapper.toUserDtoList(userRepository.findAll());
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> optionalUserDto = userRepository.findById(id);
        if (optionalUserDto.isPresent()){
            return userDtoMapper.toUserDto(optionalUserDto.get());
        }
        return null;
    }

    @Override
    public Boolean deleteUserById(Long id) {
        Optional<User> userToDelete = userRepository.findById(id);
        if (userToDelete.isPresent()){
            userRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void saveUser(User newUser) {
        userRepository.save(newUser);
    }
}
