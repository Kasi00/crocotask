package com.example.crocotask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationStatusResponse {
    private Long customerId;
    private String channel;
    private String status;
    private LocalDate sentAt;
}
