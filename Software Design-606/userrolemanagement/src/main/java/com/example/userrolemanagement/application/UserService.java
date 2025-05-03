package com.example.userrolemanagement.application;

import com.example.userrolemanagement.application.interfaces.RoleRepository;
import com.example.userrolemanagement.application.interfaces.UserRepository;
import com.example.userrolemanagement.domain.Role;
import com.example.userrolemanagement.domain.User;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User createUser(String name, String email) {
        validateInput(name, email);
        User user = new User(name, email);
        return userRepository.save(user);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID "+id+" not found"));
    }

    public List<User> getAllUsers(int page, int size) {
        return userRepository.findAll(page, size);
    }

    public void assignRoleToUser(UUID userId, UUID roleId) {
        User user = getUserById(userId);
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role with ID "+roleId+" not found"));
        user.assignRole(role);
        userRepository.save(user);
    }

    public void removeRoleFromUser(UUID userId, UUID roleId) {
        User user = getUserById(userId);
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role with ID "+roleId+" not found"));
        user.removeRole(role);
        userRepository.save(user);
    }

    public void validateInput(String name, String email) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be blank");
        }

        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }

        if(!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid Email Format");
        }
    }

    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
    public static class RoleNotFoundException extends RuntimeException {
        public RoleNotFoundException(String message) {
            super(message);
        }
    }
}
