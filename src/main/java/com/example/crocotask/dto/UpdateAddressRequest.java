package com.example.crocotask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateAddressRequest {
    @NotBlank
    private String newValue;
}
