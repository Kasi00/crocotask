package com.example.crocotask.controller;

import com.example.crocotask.dto.CustomerOptInResponse;
import com.example.crocotask.dto.NotificationStatusReportResponse;
import com.example.crocotask.dto.ReportResponse;
import com.example.crocotask.implementations.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.crocotask.security.AuthorizationConstants.ADMIN;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/notifications")
    @PreAuthorize(ADMIN)
    public ResponseEntity<ReportResponse> getNotificationReport() {
        return ResponseEntity.ok(reportService.getReport());
    }


    @GetMapping("/customer-opt-in")
    @PreAuthorize(ADMIN)
    public ResponseEntity<CustomerOptInResponse> getCustomerOptInReport() {
        return ResponseEntity.ok(reportService.getCustomerOptIn());
    }

    @GetMapping("/notification-status")
    @PreAuthorize(ADMIN)
    public ResponseEntity<NotificationStatusReportResponse> getNotificationStatusReport() {
        return ResponseEntity.ok(reportService.getNotificationStatusReport());
    }




}
