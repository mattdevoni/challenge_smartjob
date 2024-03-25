package com.challenge.demo.controller;

import com.challenge.demo.exceptions.CustomError;
import com.challenge.demo.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    @ApiResponse(responseCode = "200", description = "Successful login", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CustomError.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CustomError.class)))
    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CustomError.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class)))
    @PostMapping(produces = "application/json")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Errors");
        }
    }
}


