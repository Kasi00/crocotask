package com.example.crocotask.controller;

import com.example.crocotask.dto.NotificationStatusResponse;
import com.example.crocotask.dto.TrackStatusRequest;
import com.example.crocotask.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.crocotask.security.AuthorizationConstants.ADMIN;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{customerId}")
    @PreAuthorize(ADMIN)
    public ResponseEntity<List<NotificationStatusResponse>> getStatusesForCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(notificationService.getStatusesForCustomer(customerId));
    }

    @PostMapping("/{customerId}/trackStatus")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> trackStatus(@PathVariable Long customerId, @RequestBody TrackStatusRequest request) {
        notificationService.trackStatus(customerId, request);
        return ResponseEntity.ok().build();
    }


}
