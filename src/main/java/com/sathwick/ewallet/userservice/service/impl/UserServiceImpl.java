package com.sathwick.ewallet.userservice.service.impl;

import com.sathwick.ewallet.userservice.domain.User;
import com.sathwick.ewallet.userservice.repository.UserRepository;
import com.sathwick.ewallet.userservice.service.UserService;
import com.sathwick.ewallet.userservice.service.resource.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(User user) {
        // check if user is valid
        // check if username exists
        // Encode the password before storing
        // save the user to db
        // create a user created event
    }

    @Override
    public User getUser(String userId) {
        return null;
    }

    @Override
    public User deleteUser(String userId) {
        return null;
    }

    @Override
    public User updateUser(UserRequest userRequest, String id) {
        return null;
    }
}
