package com.ibm.intest.controllers;

import com.ibm.intest.service.UserService;
import com.ibm.intest.dto.UserGetDto;
import com.ibm.intest.models.entities.User;
import com.ibm.intest.models.mappers.UserGetDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/user")
public class UserController {

    //Could not autowire. No beans of 'UserService' type found
    @Autowired
    UserService userService;

    @Autowired
    UserGetDtoMapper userGetDtoMapper;




    @GetMapping("/get-all")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/get-all-dto")
    public ResponseEntity<List<UserGetDto>> getAllUsersDto(){
        return new ResponseEntity<>(userGetDtoMapper.toUsergetDtoList(userService.getAllUsers()),HttpStatus.OK);
    }

    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<UserGetDto> getUserDtoById(@PathVariable Long userId){
        Optional<User> userFound = userService.getUserById(userId);
        if (userFound.isPresent()){
            return new ResponseEntity<>(userGetDtoMapper.toUserGetDto(userFound.get()), HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //da testare in Postman (funziona)
    @DeleteMapping( "/delete-user")
    public ResponseEntity deleteUserById(@RequestParam Long id){
        Boolean deleteRequest = userService.deleteUserById(id);
        if (deleteRequest){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }



    /*
    //Risposta hardcoded
    @GetMapping("/test/get-all")
    public ResponseEntity<List<User>> getAllUsersTest(){
        User user1 = new User(10007L, "Giuliano", "Verdini", 48, "LPONMP");
        User user2 = new User(10008L, "Paolo", "Giusti", 33, "JIHNXZ");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //Risposta hardcoded
    @GetMapping("/test/get-all-dto")
    public ResponseEntity<List<UserGetDto>> getAllUsersDtoTest(){
        User user1 = new User(10007L, "Giuliano", "Verdini", 48, "LPONMP");
        User user2 = new User(10008L, "Paolo", "Giusti", 33, "JIHNXZ");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        return new ResponseEntity<>(userGetDtoMapper.toUsergetDtoList(users),HttpStatus.OK);
    }


     */

}
