package com.example.crocotask.implementations;

import com.example.crocotask.dto.NotificationStatusResponse;
import com.example.crocotask.dto.TrackStatusRequest;
import com.example.crocotask.entity.Customer;
import com.example.crocotask.entity.NotificationStatus;
import com.example.crocotask.exception.NotFoundException;
import com.example.crocotask.service.NotificationService;
import com.example.crocotask.repository.CustomerRepository;
import com.example.crocotask.repository.NotificationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationStatusRepository statusRepository;
    private final CustomerRepository customerRepository;






    @Override
    public void trackStatus(Long customerId,TrackStatusRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        NotificationStatus status = new NotificationStatus();
        status.setCustomer(customer);
        status.setChannel(request.getChannel());
        status.setStatus(request.getStatus());
        status.setSentAt(LocalDate.now());

        statusRepository.save(status);

    }




    //

    @Override
    public List<NotificationStatusResponse> getStatusesForCustomer(Long customerId) {
        List<NotificationStatus> statuses = statusRepository.findByCustomerId(customerId);
        return statuses.stream().map(s -> {
            NotificationStatusResponse result = new NotificationStatusResponse();
            result.setCustomerId(s.getCustomer().getId());
            result.setChannel(s.getChannel());
            result.setStatus(s.getStatus());
            result.setSentAt(s.getSentAt());
            return result;
        }).toList();
    }
}
