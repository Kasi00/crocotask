package com.example.crocotask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFilterRequest {
    @NotBlank(message = "Full name is required")
    private String name;
    @Email(message = "Invalid email")
    private String email;

    private Boolean emailEnabled;
    private Boolean smsEnabled;
    private Boolean promotionalEnabled;

    private int page = 0;
    private int size = 10;
    private String sortBy = "fullName";
    private String direction = "asc";
}
