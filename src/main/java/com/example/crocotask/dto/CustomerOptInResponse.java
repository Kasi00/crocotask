package com.example.crocotask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOptInResponse {
    private Long emailOptIn;
    private Long smsOptIn;
    private Long promotionalOptIn;
}
