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

    /* dubbio
    va bene se non utilizzo il DTO nel metodo deleteById?
    in teoria non sto esponendo dati all'esterno, devo solo
    eliminare il record dal datasource e mi arriva come input
    solo l'id, non l'oggetto UserDto.

    Caso diverso sarebbe se avessi in ingresso un UserDto, in quel
    caso potrei usare il metodo userRepository.delete(User user) andando
    a convertire l'UserDto che ho in ingresso in User
     */
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
    public void saveUser(UserDto newUser) {
        userRepository.save(userDtoMapper.UserDtoToUser(newUser));
    }
}
