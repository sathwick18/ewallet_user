package com.sathwick.ewallet.userservice.controller;

import com.sathwick.ewallet.userservice.service.UserService;
import com.sathwick.ewallet.userservice.service.resource.UserRequest;
import com.sathwick.ewallet.userservice.service.resource.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequest userRequest){
        userService.createUser(userRequest.toUser());
        return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String userId){
        UserResponse response = userService.getUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpEntity> deleteUser(@PathVariable("id") String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest userRequest, @PathVariable("id") String userId){
        UserResponse response = userService.updateUser(userRequest, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
