package com.ibm.intest.service.impl;

import com.ibm.intest.dto.UserDto;
import com.ibm.intest.dto.UserDtoCriteria;
import com.ibm.intest.dto.UserResponse;
import com.ibm.intest.models.entities.QUser;
import com.ibm.intest.models.entities.User;
import com.ibm.intest.models.mappers.UserDtoMapper;
import com.ibm.intest.repositories.UserRepository;
import com.ibm.intest.service.UserService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDtoMapper userDtoMapper;

    @Autowired
    EntityManager em;

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

    @Override
    public List<UserDto> getAllUsersWithAge(int age, Pageable pageable) {
        QUser qUser = QUser.user;
        JPAQuery<User> jpaQuery = new JPAQuery<>(em);
        List<User> usersWithAge = jpaQuery.from(qUser)
                .where(qUser.age.eq(age))
                .offset(pageable.getPageNumber()*pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();


        return userDtoMapper.toUserDtoList(usersWithAge);
    }

    @Override
    public List<UserDto> getAllUsersFirstNameOverGivenAge(String name, int age) {
        QUser qUser = QUser.user;
        JPAQuery<User> jpaQuery = new JPAQuery<>(em);
        List<User> usersWithGivenFirstNameOverAge = jpaQuery.from(qUser)
                .where(qUser.firstName.eq(name))
                .where(qUser.age.gt(age))
                .fetch();

        return userDtoMapper.toUserDtoList((usersWithGivenFirstNameOverAge));
    }

    @Override
    public List<UserDto> findUsers(UserDtoCriteria criteria) {

        //tutto questo andrebbe in una classe UserDtoCriteriaBuilder all'interno del package query
        //tale classe andrebbe annotata con @Component, così da poter fare l'autowire in UserServiceImpl
        QUser qUser = QUser.user;
        BooleanBuilder predicate = new BooleanBuilder();

        Long id = criteria.getId();
        String firstName = criteria.getFirstName();
        String lastName = criteria.getLastName();
        Integer age = criteria.getAge();

        if (id != null){
            predicate.and(qUser.userId.eq(id));
        }
        if (firstName != null){
            predicate.and(qUser.firstName.eq(firstName));
        }
        if (lastName != null){
            predicate.and(qUser.lastName.eq(lastName));
        }
        if (age != null){
            predicate.and(qUser.age.eq(age));
        }

        List<User> usersFound = (List<User>) userRepository.findAll(predicate);
        return userDtoMapper.toUserDtoList(usersFound);
    }

    //usa i criteria, ma restituisce solo i cognomi
    @Override
    public UserResponse findUsersLastName(UserDtoCriteria criteria) {

        QUser qUser = QUser.user;
        BooleanBuilder predicate = new BooleanBuilder();
        UserResponse response = new UserResponse();
        List<String> nomi = new ArrayList<>();

        String lastName = criteria.getLastName();

        if (lastName != null){
            predicate.and(qUser.lastName.eq(lastName));
        }

        List<User> usersFound = (List<User>) userRepository.findAll(predicate);
        for (User user: usersFound) {
            nomi.add(user.getFirstName());
        }
        response.setNomi(nomi);
        return response;
    }
}
