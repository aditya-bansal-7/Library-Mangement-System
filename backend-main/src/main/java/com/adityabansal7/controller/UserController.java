package com.adityabansal7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.adityabansal7.exception.UserException;
import com.adityabansal7.model.User;
import com.adityabansal7.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    
    @PostMapping("/registerUser")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws UserException {
        User savedUser =  userService.addUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }
    
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) throws UserException {
        return userService.findUserById(userId)
                          .map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody Map<String, String> payload) throws UserException {
        String email = payload.get("email");
        String password = payload.get("password");
        User loggedInUser = userService.loginUser(email, password);
        return new ResponseEntity<User>(loggedInUser, HttpStatus.OK);
    }

    @GetMapping(value = "/allUsers", produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers() {
        
        List<User> users = userService.getAllUsers();
        
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PutMapping("/blockUser/{userId}")
    public ResponseEntity<User> blockUser(@PathVariable Integer userId) throws UserException {
        User user = userService.blockUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/unblockUser/{userId}")
    public ResponseEntity<User> unblockUser(@PathVariable Integer userId) throws UserException {
        User user = userService.unblockUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/updateFine/{userId}")
    public ResponseEntity<User> updateFine(@PathVariable Integer userId, @RequestBody Map<String, Double> payload) throws UserException {
        Double fine = payload.get("fine");
        User user = userService.updateFine(userId, fine);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
