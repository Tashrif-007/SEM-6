package com.example.userrolemanagement.application;

import com.example.userrolemanagement.application.interfaces.RoleRepository;
import com.example.userrolemanagement.application.interfaces.UserRepository;
import com.example.userrolemanagement.domain.Role;
import com.example.userrolemanagement.domain.User;
import com.example.userrolemanagement.application.UserService.RoleNotFoundException;
import com.example.userrolemanagement.application.UserService.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserService userService;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        userService = new UserService(userRepository, roleRepository);
    }

    @Test
    public void testCreateUser() {
        String email = "John";
        String name = "john.doe@example.com";
        User mockUser = new User(email, name);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User createdUser = userService.createUser(email, name);

        assertNotNull(createdUser);
        assertEquals(name, createdUser.getName());
        assertEquals(email, createdUser.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testGetUserById_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        try {
            userService.getUserById(userId);
            fail("Expected UserNotFoundException to be thrown");
        } catch (UserNotFoundException e) {
            assertEquals("User with ID " + userId + " not found", e.getMessage());
        }
    }

    @Test
    public void testGetUserById_UserFound() {
        UUID userId = UUID.randomUUID();
        User mockUser = new User("john.doe@example.com", "John Doe");
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User user = userService.getUserById(userId);

        assertNotNull(user);
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    public void testAssignRoleToUser() {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        User mockUser = new User("johndoe@example.com", "John Doe");
        Role mockRole = new Role("Admin");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(mockRole));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        userService.assignRoleToUser(userId, roleId);

        verify(userRepository).save(any(User.class));
        assertTrue(mockUser.getRoles().contains(mockRole));
    }

    @Test
    public void testAssignRoleToUser_RoleNotFound() {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        User mockUser = new User("johndoe@example.com", "John Doe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        try {
            userService.assignRoleToUser(userId, roleId);
            fail("Expected RoleNotFoundException to be thrown");
        } catch (RoleNotFoundException e) {
            assertEquals("Role with ID " + roleId + " not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveRoleFromUser() {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        User mockUser = new User("johndoe@example.com", "John Doe");
        Role mockRole = new Role("Admin");
        mockUser.assignRole(mockRole);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(mockRole));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        userService.removeRoleFromUser(userId, roleId);

        verify(userRepository).save(any(User.class));
        assertFalse(mockUser.getRoles().contains(mockRole));
    }

    @Test
    public void testRemoveRoleFromUser_RoleNotFound() {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        User mockUser = new User("johndoe@example.com", "John Doe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        try {
            userService.removeRoleFromUser(userId, roleId);
            fail("Expected RoleNotFoundException to be thrown");
        } catch (RoleNotFoundException e) {
            assertEquals("Role with ID " + roleId + " not found", e.getMessage());
        }
    }
}
