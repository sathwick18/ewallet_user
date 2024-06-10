package com.sathwick.ewallet.userservice.service;

import com.sathwick.ewallet.userservice.domain.User;
import com.sathwick.ewallet.userservice.service.resource.UserRequest;

public interface UserService {
    void createUser(User user);
    User getUser(String userId);
    User deleteUser(String userId);
    User updateUser(UserRequest userRequest, String id);
}
