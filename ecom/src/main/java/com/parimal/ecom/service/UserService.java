package com.parimal.ecom.service;

import com.parimal.ecom.dto.UserRequest;
import com.parimal.ecom.dto.UserResponse;
import com.parimal.ecom.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> fetchAllUsers();

    void addUser(User user);

    Optional<User> fetchUser(Long id);

    boolean updateUser(UserRequest userRequest, Long id);

    boolean deleteUser(Long id);

    // Conversion methods
    UserResponse convertToUserResponse(User user);

    User convertUserRequestToUser(UserRequest userRequest);
}
