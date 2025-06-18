package com.example.crocotask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private Long id;
    private String fullName;
    private boolean emailEnabled;
    private boolean smsEnabled;
    private boolean promotionalEnabled;
}
