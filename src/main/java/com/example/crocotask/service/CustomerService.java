package com.example.crocotask.service;

import com.example.crocotask.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    void addCustomer(CustomerRequest request);
    void updateCustomer(Long id, CustomerRequest request);
    void deleteCustomer(Long id);
    CustomerResponse getCustomer(Long id);
    List<CustomerResponse> getAllCustomers();
    void updatePreferences(Long id, UpdatePreferenceRequest request);
    PreferenceResponse getPreferences(Long id);
    CustomerDetailedResponse getDetailedCustomerProfile(Long id);
    BatchUpdateResponse batchUpdateCustomer(BatchCustomerUpdateRequest request);
    Page<CustomerListResponse> getFilteredCustomers(CustomerFilterRequest request);
    CustomerPanelDto getCustomerPanel(Long id);
    void updateCustomer(Long id,CustomerPanelUpdateDto request);

}
