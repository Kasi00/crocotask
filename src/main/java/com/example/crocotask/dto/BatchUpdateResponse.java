package com.example.crocotask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchUpdateResponse {
    private List<Long> updatedCustomerIds;
    private List<Long> failedCustomerIds;
}
