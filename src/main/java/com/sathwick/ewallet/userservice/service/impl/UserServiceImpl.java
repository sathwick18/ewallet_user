package com.sathwick.ewallet.userservice.service.impl;

import com.sathwick.ewallet.userservice.domain.User;
import com.sathwick.ewallet.userservice.exception.UserException;
import com.sathwick.ewallet.userservice.repository.UserRepository;
import com.sathwick.ewallet.userservice.service.UserService;
import com.sathwick.ewallet.userservice.service.resource.UserRequest;
import com.sathwick.ewallet.userservice.service.resource.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Value("${kafka.topic.user-created}")
    private String USER_CREATED_TOPIC;

    @Value("${kafka.topic.user-deleted}")
    private String USER_DELETED_TOPIC;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void createUser(User user) {
        Optional<User> optionalUser = userRepository.findByName(user.getName());
        // check if user is valid
        // check if username exists
        if(optionalUser.isPresent()){
            throw new UserException("EWALLET_USER_EXISTS_EXCEPTION", "User "+optionalUser.get().getName()+" Already Exists");
        }
        // Encode the password before storing
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // save the user to db
        userRepository.save(user);
        // create a user created event and wallet service will consume this event and creates the wallet service asynchronously
        kafkaTemplate.send(USER_CREATED_TOPIC, String.valueOf(user.getUserId()));
    }

    @Override
    public UserResponse getUser(String userId) {
        Optional<User> optionalUser = userRepository.findById(Long.valueOf(userId));
        User user = optionalUser.orElseThrow(() -> new UserException("EWALLET_USER_NOT_FOUND_EXCEPTION", "User Not Found"));
        return new UserResponse(user);
    }

    @Override
    public UserResponse deleteUser(String userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new UserException("EWALLET_USER_NOT_FOUND_EXCEPTION", "User Not Found"));
        userRepository.deleteById(Long.valueOf(userId));
        // send event to wallet service to delete wallet
        kafkaTemplate.send(USER_DELETED_TOPIC, userId);
        return new UserResponse(user);
    }

    @Override
    public User updateUser(UserRequest userRequest, String id) {
        return null;
    }
}
