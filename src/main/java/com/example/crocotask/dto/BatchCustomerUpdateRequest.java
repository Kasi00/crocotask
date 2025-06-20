package com.example.crocotask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchCustomerUpdateRequest {
    private List<SingleCustomerUpdateRequest> updates;
}
