package com.example.crocotask.implementations;

import com.example.crocotask.dto.CustomerOptInResponse;
import com.example.crocotask.dto.NotificationStatusReportResponse;
import com.example.crocotask.dto.ReportResponse;
import com.example.crocotask.repository.CustomerRepository;
import com.example.crocotask.repository.NotificationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final CustomerRepository customerRepository;
    private final NotificationStatusRepository statusRepository;

    public ReportResponse getReport() {
        Long total=statusRepository.count();
        Long delivered = statusRepository.countByStatus("delivered");
        Long failed = statusRepository.countByStatus("failed");
        Long pending = statusRepository.countByStatus("pending");

        double successRate = total > 0 ? (delivered * 100.0) / total : 0;

        ReportResponse response = new ReportResponse();
        response.setTotalSent(total);
        response.setDeliveredCount(delivered);
        response.setFailedCount(failed);
        response.setPendingCount(pending);
        response.setSuccessRate(successRate);

        return response;
    }

    public CustomerOptInResponse getCustomerOptIn() {
        CustomerOptInResponse response = new CustomerOptInResponse();
        response.setEmailOptIn(customerRepository.countEmailOptIn());
        response.setSmsOptIn(customerRepository.countSmsOptIn());
        response.setPromotionalOptIn(customerRepository.countPromotionalOptIn());
        return response;
    }



    public NotificationStatusReportResponse getNotificationStatusReport() {
        NotificationStatusReportResponse response = new NotificationStatusReportResponse();

        Map<String, Long> statusCounts = new HashMap<>();
        for (Object[] row : statusRepository.countByStatus()) {
            statusCounts.put((String) row[0], (Long) row[1]);
        }
        response.setStatusCounts(statusCounts);

        Map<String, Long> channelCounts = new HashMap<>();
        for (Object[] row : statusRepository.countByChannel()) {
            channelCounts.put((String) row[0], (Long) row[1]);
        }
        response.setChannelCounts(channelCounts);

        return response;


    }




}
