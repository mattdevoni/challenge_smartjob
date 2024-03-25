package com.challenge.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {
    @Schema(description = "Phone number", example = "123456789")
    private String number;

    @Schema(description = "City code", example = "123")
    @JsonProperty("citycode")
    private String cityCode;

    @Schema(description = "Country code", example = "1")
    @JsonProperty("countrycode")
    private String countryCode;
}