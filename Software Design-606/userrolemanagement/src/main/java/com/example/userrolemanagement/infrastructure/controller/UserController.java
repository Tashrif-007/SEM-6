package com.example.userrolemanagement.infrastructure.controller;

import com.example.userrolemanagement.application.UserService;
import com.example.userrolemanagement.domain.User;
import com.example.userrolemanagement.infrastructure.controller.dto.CreateUserRequest;
import com.example.userrolemanagement.infrastructure.controller.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> createUser(@Valid @RequestBody CreateUserRequest request) {
        User createdUser = userService.createUser(request.getName(), request.getEmail());
        Map<String, UUID> response = new HashMap<>();
        response.put("id", createdUser.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserDTO.fromDomain(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers().stream()
                .map(UserDTO::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{userId}/assign-role/{roleId}")
    public ResponseEntity<Map<String, String>> assignRoleToUser(
            @PathVariable UUID userId,
            @PathVariable UUID roleId) {
        userService.assignRoleToUser(userId, roleId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Role assigned successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/remove-role/{roleId}")
    public ResponseEntity<Map<String, String>> removeRoleFromUser(
            @PathVariable UUID userId,
            @PathVariable UUID roleId) {
        userService.removeRoleFromUser(userId, roleId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Role removed successfully");
        return ResponseEntity.ok(response);
    }
}