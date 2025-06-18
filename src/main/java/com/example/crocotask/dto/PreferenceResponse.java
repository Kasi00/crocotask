package com.example.crocotask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PreferenceResponse {
    private boolean emailEnabled;
    private boolean smsEnabled;
    private boolean promotionalEnabled;
}
