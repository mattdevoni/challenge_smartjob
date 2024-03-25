package com.challenge.demo.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @Schema(description = "User ID", example = "12345")
    private String id;

    @Schema(description = "User creation date")
    private LocalDateTime created;

    @Schema(description = "User modification date")
    private LocalDateTime modified;

    @Schema(description = "User last login")
    private LocalDateTime lastLogin;

    @Schema(description = "User access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String token;

    @Schema(description = "User activation status")
    private boolean isActive;

    @Schema(description = "List of phones associated with the user")
    private List<PhoneDTO> phones;
}