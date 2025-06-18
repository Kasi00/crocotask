package com.example.crocotask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationStatusReportResponse {
    private Map<String, Long> statusCounts;   // e.g. delivered: 50, failed: 3
    private Map<String, Long> channelCounts;  // e.g. email: 40, sms: 13
}
