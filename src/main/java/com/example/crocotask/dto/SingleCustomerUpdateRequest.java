package com.example.crocotask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleCustomerUpdateRequest {
    private Long customerId;
    private String fullName;
    private String phone;
    private boolean emailEnabled;
    private boolean smsEnabled;
    private boolean promotionalEnabled;
}
