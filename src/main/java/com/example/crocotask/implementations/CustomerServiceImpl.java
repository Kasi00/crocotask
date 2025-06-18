package com.example.crocotask.implementations;

import com.example.crocotask.dto.*;
import com.example.crocotask.entity.Customer;
import com.example.crocotask.entity.NotificationPreference;
import com.example.crocotask.exception.NotFoundException;
import com.example.crocotask.service.CustomerService;
import com.example.crocotask.repository.CustomerRepository;
import com.example.crocotask.specifications.CustomerSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;


    @Override
    public void addCustomer(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Customer customer = new Customer();
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone().replace(" ",""));

        NotificationPreference preference = new NotificationPreference();
        preference.setEmailEnabled(request.isEmailEnabled());
        preference.setSmsEnabled(request.isSmsEnabled());
        preference.setPromotionalEnabled(request.isPromotionalEnabled());
        customer.setPreferences(preference);

        customerRepository.save(customer);


    }

    @Override
    public void updateCustomer(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        NotificationPreference preference = new NotificationPreference();
        preference.setEmailEnabled(request.isEmailEnabled());
        preference.setSmsEnabled(request.isSmsEnabled());
        preference.setPromotionalEnabled(request.isPromotionalEnabled());
        customer.setPreferences(preference);

    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);


    }

    @Override
    public CustomerResponse getCustomer(Long id) {
        Customer customer= customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
        CustomerResponse response= new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmailEnabled(customer.getPreferences().isEmailEnabled());
        response.setSmsEnabled(customer.getPreferences().isSmsEnabled());
        response.setPromotionalEnabled(customer.getPreferences().isPromotionalEnabled());
        return response;


    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream().map(c ->
                new CustomerResponse(
                        c.getId(),
                        c.getFullName(),
                        c.getPreferences().isEmailEnabled(),
                        c.getPreferences().isSmsEnabled(),
                        c.getPreferences().isPromotionalEnabled()
                )
        ).toList();
    }

    @Override
    public void updatePreferences(Long id, UpdatePreferenceRequest request) {
        Customer customer= customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
        NotificationPreference preference = new NotificationPreference();
        preference.setEmailEnabled(request.isEmailEnabled());
        preference.setSmsEnabled(request.isSmsEnabled());
        preference.setPromotionalEnabled(request.isPromotionalEnabled());
        customer.setPreferences(preference);
        customerRepository.save(customer);
    }

    @Override
    public PreferenceResponse getPreferences(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
        NotificationPreference preference= customer.getPreferences();
        return new PreferenceResponse(preference.isEmailEnabled(), preference.isSmsEnabled(), preference.isPromotionalEnabled());

    }

    @Override
    public CustomerDetailedResponse getDetailedCustomerProfile(Long id) {
       Customer customer= customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
        List<AddressResponse> addressResponse = customer.getAddresses().stream().map(address -> {
            AddressResponse dto = new AddressResponse();
            dto.setType(address.getType());
            dto.setValue(address.getValue());
            return dto;
        }).toList();

        CustomerDetailedResponse response= new CustomerDetailedResponse();
        response.setEmail(customer.getEmail());
        response.setPhone(customer.getPhone());
        response.setAddresses(addressResponse);
        response.setPreferences(customer.getPreferences());
        return response;
    }

    @Override
    public BatchUpdateResponse batchUpdateCustomer(BatchCustomerUpdateRequest request) {
        List<Long> updated = new ArrayList<>();
        List<Long> failed = new ArrayList<>();

        for (SingleCustomerUpdateRequest update : request.getUpdates()) {
            try {
                Customer customer = customerRepository.findById(update.getCustomerId())
                        .orElseThrow(() -> new NotFoundException("Customer not found"));

                if (update.getFullName() != null) customer.setFullName(update.getFullName());
                if (update.getPhone() != null) customer.setPhone(update.getPhone());

                NotificationPreference preference = new NotificationPreference();
                preference.setEmailEnabled(update.isEmailEnabled());
                preference.setSmsEnabled(update.isSmsEnabled());
                preference.setPromotionalEnabled(update.isPromotionalEnabled());
                customer.setPreferences(preference);

                customerRepository.save(customer);
                updated.add(update.getCustomerId());

            } catch (Exception e) {
                failed.add(update.getCustomerId());
            }
        }

        BatchUpdateResponse response = new BatchUpdateResponse();
        response.setUpdatedCustomerIds(updated);
        response.setFailedCustomerIds(failed);
        return response;
    }


    @Override
    public Page<CustomerListResponse> getFilteredCustomers(CustomerFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy())
        );

        Specification<Customer> spec = CustomerSpecifications.filter(request);
        return customerRepository.findAll(spec, pageable)
                .map(this::mapToListResponse);

    }

    @Override
    public CustomerPanelDto getCustomerPanel(Long id) {
        Customer customer= customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
        CustomerPanelDto dto= new CustomerPanelDto();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setEmailEnabled(customer.getPreferences().isEmailEnabled());
        dto.setSmsEnabled(customer.getPreferences().isSmsEnabled());
        dto.setPromotionalEnabled(customer.getPreferences().isPromotionalEnabled());
        return dto;
    }

    @Override
    public void updateCustomer(Long id, CustomerPanelUpdateDto request) {
        Customer customer= customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customerRepository.save(customer);
    }

    private CustomerListResponse mapToListResponse(Customer customer) {
        return new CustomerListResponse(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getPreferences()
        );


    }


}
