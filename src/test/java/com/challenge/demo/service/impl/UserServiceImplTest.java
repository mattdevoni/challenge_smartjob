package com.challenge.demo.service.impl;

import com.challenge.demo.persistence.UserPersistence;
import com.challenge.demo.security.CustomerDetailsService;
import com.challenge.demo.service.UserService;
import com.challenge.demo.service.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Test
    public void createUser_NewUser_SuccessfullyCreated() {
        // Arrange
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("password123");
        newUser.setToken("encodedPassword123");

        UserPersistence userPersistence = mock(UserPersistence.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        CustomerDetailsService customerDetailsService = mock(CustomerDetailsService.class);
        BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

        UserService userService = new UserServiceImpl(userPersistence,
                authenticationManager,
                customerDetailsService,
                bCryptPasswordEncoder);

        when(userPersistence.findOneByEmail("newuser@example.com")).thenReturn(null);
        when(userPersistence.saveUser(any(User.class))).thenReturn(newUser);
        when(bCryptPasswordEncoder.encode("password123")).thenReturn("encodedPassword123");

        // Action
        User createdUser = userService.createUser(newUser);

        // Asserts
        assertNotNull(createdUser);
        assertEquals("newuser@example.com", createdUser.getEmail());
        assertEquals("encodedPassword123", createdUser.getPassword());
    }

    @Test
    public void createUser_SaveUserError_ThrowsRuntimeException() {
        // Arrange
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("password123");

        UserPersistence userPersistence = mock(UserPersistence.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        CustomerDetailsService customerDetailsService = mock(CustomerDetailsService.class);
        BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

        UserService userService = new UserServiceImpl(userPersistence,
                authenticationManager,
                customerDetailsService,
                bCryptPasswordEncoder);

        when(userPersistence.findOneByEmail("newuser@example.com")).thenReturn(null);
        when(userPersistence.saveUser(any(User.class))).thenThrow(new RuntimeException("Failed to save user"));

        // Action & Assert
        assertThrows(RuntimeException.class, () -> userService.createUser(newUser));
    }
}