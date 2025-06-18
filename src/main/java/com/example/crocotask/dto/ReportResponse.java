package com.example.crocotask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private Long totalSent;
    private Long deliveredCount;
    private Long failedCount;
    private Long pendingCount;
    private double successRate;
}
