package com.parimal.ecom.controller;

import com.parimal.ecom.dto.UserRequest;
import com.parimal.ecom.dto.UserResponse;
import com.parimal.ecom.model.User;
import com.parimal.ecom.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.fetchAllUsers();
        List<UserResponse> userResponses = users.stream()
                .map(userService::convertToUserResponse)
                .toList();
        return ResponseEntity.ok(userResponses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.fetchUser(id);
        return user.map(u -> ResponseEntity.ok(userService.convertToUserResponse(u)))
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {
        User user = userService.convertUserRequestToUser(userRequest);
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        boolean updated = userService.updateUser(userRequest, id);
        if (updated) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.fetchUser(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
