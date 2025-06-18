package com.example.crocotask.dto;

import com.example.crocotask.entity.NotificationPreference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerListResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private NotificationPreference preferences;
}
