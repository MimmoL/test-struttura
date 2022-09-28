package com.ibm.intest.controllers;

import com.ibm.intest.dto.UserDto;
import com.ibm.intest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userService;


    //QueryDSL
    //Ritorna tutti gli UserDto che hanno una determinata età con paginazione
    @GetMapping("/get-users-age/{age}")
    public ResponseEntity<List<UserDto>> searchUsersWithGivenAge(@PathVariable int age){
        Pageable pageable = PageRequest.of(0,3);
        return new ResponseEntity<>(userService.getAllUsersWithAge(age, pageable), HttpStatus.OK);
    }

    @GetMapping("/get-users-age2/{age}")
    public ResponseEntity<List<UserDto>> searchUsersWithGivenAge2(@PathVariable int age){
        Pageable pageable = PageRequest.of(1,3);
        return new ResponseEntity<>(userService.getAllUsersWithAge(age, pageable), HttpStatus.OK);
    }

    //QueryDSL
    //Ritorna tutti gli utenti con specifico nome e età>x
    @GetMapping("/get-users-name-age/{name}-{age}")
    public ResponseEntity<List<UserDto>> getAllUsersWithGivenNameOverAge(@PathVariable String name, @PathVariable int age){
        return new ResponseEntity<>(userService.getAllUsersFirstNameOverGivenAge(name, age), HttpStatus.OK);
    }


    //Ritorna tutti gli UsersDto
    @GetMapping("/get-all-dto")
    public ResponseEntity<List<UserDto>> getAllUsersDto(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    //Ritorna UserDto con determinato id
    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<UserDto> getUserDtoById(@PathVariable Long userId){
        UserDto userFound = userService.getUserById(userId);
        if (userFound != null){
            return new ResponseEntity<>(userFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }



    //elimina un utente dal datasource
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

    //inserisce un nuovo utente nel datasource
    @PostMapping("/new-user")
    public ResponseEntity saveUser(@RequestBody UserDto newUser){
        userService.saveUser(newUser);
        return new ResponseEntity<>(HttpStatus.OK);
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
