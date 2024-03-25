package com.challenge.demo.controller.mapper;

import com.challenge.demo.controller.UsersController;
import com.challenge.demo.controller.dto.PhoneDTO;
import com.challenge.demo.controller.dto.UserDTO;
import com.challenge.demo.controller.dto.UserResponse;
import com.challenge.demo.controller.utils.Validators;
import com.challenge.demo.exceptions.EmailException;
import com.challenge.demo.exceptions.PasswordException;
import com.challenge.demo.service.UserService;
import com.challenge.demo.service.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersControllerTest {

    @Test
    public void createUser_ValidUser_ReturnsCreated() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiam9obmR......";
        PhoneDTO phone1 = new PhoneDTO("1234567", "1", "57");
        PhoneDTO phone2 = new PhoneDTO("1234567", "1", "59");
        UserDTO userDTO = new UserDTO("Matías", "matias@matias.cl", "passwordMM22", Arrays.asList(phone1, phone2));

        // Mocks
        UserService userService = mock(UserService.class);
        User createdUser = UserMapper.toUser(userDTO);
        createdUser.setId(UUID.fromString("e479766c-b625-48c5-892f-5b8fc6b1bfb1"));
        LocalDateTime createdDate = LocalDateTime.now();
        createdUser.setCreatedAt(createdDate);
        createdUser.setUpdatedAt(LocalDateTime.now());
        createdUser.setLastLogin(createdDate);
        createdUser.setToken(token);
        createdUser.setActive(true);
        when(userService.createUser(any(User.class))).thenReturn(createdUser);

        Validators validators = mock(Validators.class);
        when(validators.validateEmail(anyString())).thenReturn(true);
        when(validators.validatePassword(anyString())).thenReturn(true);

        UsersController usersController = new UsersController(userService, validators);

        // Action
        ResponseEntity<UserResponse> response = usersController.createUser(userDTO);

        // Asserts
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdUser.getId().toString(), response.getBody().getId());
        assertEquals(createdUser.getCreatedAt(), response.getBody().getCreated());
        assertEquals(createdUser.getUpdatedAt(), response.getBody().getModified());
        assertEquals(createdUser.getLastLogin(), response.getBody().getLastLogin());
        assertEquals(token, response.getBody().getToken());

        assertNotNull(response.getBody().getPhones());
        assertEquals(2, response.getBody().getPhones().size());

        assertTrue(response.getBody().isActive());
    }

    @Test
    public void createUser_ValidUser_InvalidEmail_ReturnsBadRequest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiam9obmR......";
        PhoneDTO phone1 = new PhoneDTO("1234567", "1", "57");
        PhoneDTO phone2 = new PhoneDTO("1234567", "1", "59");
        UserDTO userDTO = new UserDTO("Matías", "invalid-email", "passwordMM22", Arrays.asList(phone1, phone2));

        // Mocks and setup
        UserService userService = mock(UserService.class);
        when(userService.createUser(any(User.class))).thenReturn(UserMapper.toUser(userDTO));

        Validators validators = mock(Validators.class);
        when(validators.validateEmail(anyString())).thenReturn(false);
        when(validators.validatePassword(anyString())).thenReturn(true);

        UsersController usersController = new UsersController(userService, validators);

        // Action
        assertThrows(EmailException.class, () -> usersController.createUser(userDTO));
    }

    @Test
    public void createUser_ValidUser_IncorrectPassword_ReturnsBadRequest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiam9obmR......";
        PhoneDTO phone1 = new PhoneDTO("1234567", "1", "57");
        PhoneDTO phone2 = new PhoneDTO("1234567", "1", "59");
        UserDTO userDTO = new UserDTO("Matías", "aaaaa@example.com", "pass", Arrays.asList(phone1, phone2));

        // Mocks and setup
        UserService userService = mock(UserService.class);
        when(userService.createUser(any(User.class))).thenReturn(UserMapper.toUser(userDTO));

        Validators validators = mock(Validators.class);
        when(validators.validateEmail(anyString())).thenReturn(true);
        when(validators.validatePassword(anyString())).thenReturn(false);

        UsersController usersController = new UsersController(userService, validators);

        // Action
        assertThrows(PasswordException.class, () -> usersController.createUser(userDTO));
    }
}
