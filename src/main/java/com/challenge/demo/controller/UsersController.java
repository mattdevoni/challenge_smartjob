package com.challenge.demo.controller;

import com.challenge.demo.exceptions.CustomError;
import com.challenge.demo.exceptions.EmailException;
import com.challenge.demo.exceptions.PasswordException;
import com.challenge.demo.controller.dto.UserDTO;
import com.challenge.demo.controller.dto.UserResponse;
import com.challenge.demo.controller.mapper.UserMapper;
import com.challenge.demo.controller.utils.Validators;
import com.challenge.demo.service.UserService;
import com.challenge.demo.service.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    private final Validators validators;

    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User created", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class)))
    @ApiResponse(responseCode = "409", description = "Conflict",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class)))
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDTO user) {
        if (!validators.validateEmail(user.getEmail())) {
            throw new EmailException();
        }
        if (!validators.validatePassword(user.getPassword())) {
            throw new PasswordException();
        }

        User userCreated = userService.createUser(UserMapper.toUser(user));
        UserResponse userResponse = UserMapper.toUsersResponse(userCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "List of users", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class)))
    @GetMapping
    public ResponseEntity<List<UserResponse>> listUsers() {
        List<User> users = userService.listUser();
        List<UserResponse> usersResponse = new ArrayList<>();
        for (User user : users) {
            usersResponse.add(UserMapper.toUsersResponse(user));
        }

        return ResponseEntity.status(HttpStatus.OK).body(usersResponse);
    }
}
