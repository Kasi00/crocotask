package com.example.crocotask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPanelUpdateDto {
    private String fullName;
    private String phone;
    private String email;
}
