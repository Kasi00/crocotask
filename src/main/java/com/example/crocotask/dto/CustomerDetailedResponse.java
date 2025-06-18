package com.example.crocotask.dto;

import com.example.crocotask.entity.Address;
import com.example.crocotask.entity.NotificationPreference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailedResponse {
    private String email;
    private String phone;
    private List<AddressResponse> addresses;
    private NotificationPreference preferences;
}
