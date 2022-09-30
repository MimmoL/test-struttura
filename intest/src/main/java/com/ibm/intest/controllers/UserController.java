package com.ibm.intest.controllers;
import com.ibm.intest.dto.UserDto;
import com.ibm.intest.dto.UserDtoCriteria;
import com.ibm.intest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/user")
@Tag(name = "user API", description = "Operazioni per gestire gli user")
public class UserController {

    @Autowired
    UserService userService;

    //QueryDsl con predicate
    //Qui faccio un controllo per verificare se lastName (che è annotato con @NotNull) è null
    //tramite un if, ma in realtà dovrebbe pensarci @Valid, che purtroppo non sta funzionando
    @PostMapping("/users")
    @Operation(summary = "Ricerca utenti", description = "Ricerca utenti con criteri: nome, cognome*, età")
    public ResponseEntity<List<UserDto>> findUsers(@RequestBody @Valid UserDtoCriteria criteria){
        /*
        if (criteria.getLastName() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(userService.findUsers(criteria), HttpStatus.OK);
        }

         */
        return new ResponseEntity<>(userService.findUsers(criteria), HttpStatus.OK);
    }

    //QueryDSL
    //Ritorna tutti gli UserDto che hanno una determinata età con paginazione
    @GetMapping("/get-users-age/{age}")
    @Operation(summary = "Ricerca utenti di età X", description = "Ricerca paginata degli utenti che hanno una specifica età")
    public ResponseEntity<List<UserDto>> searchUsersWithGivenAge(@PathVariable int age){
        Pageable pageable = PageRequest.of(0,5);
        return new ResponseEntity<>(userService.getAllUsersWithAge(age, pageable), HttpStatus.OK);
    }


    //test funzionamento paginazione(da eliminare)
    @GetMapping("/get-users-age2/{age}")
    @ApiIgnore
    public ResponseEntity<List<UserDto>> searchUsersWithGivenAge2(@PathVariable int age){
        Pageable pageable = PageRequest.of(1,5);
        return new ResponseEntity<>(userService.getAllUsersWithAge(age, pageable), HttpStatus.OK);
    }

    //QueryDSL
    //Ritorna tutti gli utenti con specifico nome e età>x
    @GetMapping("/get-users-name-age/{name}-{age}")
    @Operation(summary = "Ricerca utenti per nome e età>X", description = "Ricerca non paginata di tutti gli utenti che hanno un determinato nome ed età maggiore di quella specificata")
    public ResponseEntity<List<UserDto>> findUsersWithGivenNameOverAge(@PathVariable String name, @PathVariable int age){
        return new ResponseEntity<>(userService.getAllUsersFirstNameOverGivenAge(name, age), HttpStatus.OK);
    }


    //Ritorna tutti gli UsersDto
    @GetMapping("/get-all-dto")
    @Operation(summary = "Ricerca tutti utenti", description = "Ricerca non paginata di tutti gli utenti nel datasource")
    public ResponseEntity<List<UserDto>> getAllUsersDto(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    //Ritorna UserDto con determinato id
    @GetMapping("/get-by-id/{userId}")
    @Operation(summary = "Ricerca utente per id", description = "Ricerca di un utente con id specificato")
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
    @Operation(summary = "Eliminazione utente", description = "Eliminazione di un utente con specifico id")
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
    @Operation(summary = "Aggiunta di un utente", description = "Aggiunta di un utente, l'utente va passato nel body della richiesta")
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
