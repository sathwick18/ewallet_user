package com.sathwick.ewallet.userservice.controller;

import com.sathwick.ewallet.userservice.domain.User;
import com.sathwick.ewallet.userservice.service.resource.UserRequest;
import com.sathwick.ewallet.userservice.service.resource.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    @PostMapping("/signup")
    public ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long userId){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
