package com.example.crocotask.service;

import com.example.crocotask.dto.NotificationStatusResponse;
import com.example.crocotask.dto.TrackStatusRequest;

import java.util.List;

public interface NotificationService {
    void trackStatus(Long customerId,TrackStatusRequest request);
    List<NotificationStatusResponse> getStatusesForCustomer(Long customerId);

}
