package com.example.crocotask.controller;

import com.example.crocotask.dto.*;
import com.example.crocotask.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.crocotask.security.AuthorizationConstants.ADMIN;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;


    @GetMapping("/filter")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Page<CustomerListResponse>> filterCustomers(@RequestBody CustomerFilterRequest request) {
        return ResponseEntity.ok(customerService.getFilteredCustomers(request));
    }

    @GetMapping("/{id}/customerProfile")
    @PreAuthorize(ADMIN)
    public ResponseEntity<CustomerDetailedResponse> getDetailedCustomerProfile(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.getDetailedCustomerProfile(id), HttpStatus.OK);
    }




    @GetMapping("/{id}/preferences")
    @PreAuthorize(ADMIN)
    public ResponseEntity<PreferenceResponse> getPreferences(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.getPreferences(id));
    }



    @GetMapping
    @PreAuthorize(ADMIN)
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PostMapping("/add")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> addCustomer(@RequestBody CustomerRequest request) {
        customerService.addCustomer(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/batch-update")
    @PreAuthorize(ADMIN)
    public ResponseEntity<BatchUpdateResponse> batchUpdate(@RequestBody BatchCustomerUpdateRequest request) {
        return ResponseEntity.ok(customerService.batchUpdateCustomer(request));
    }

    @PutMapping("/{id}/update")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id,@Valid @RequestBody CustomerRequest request) {
        customerService.updateCustomer(id,request);
        return ResponseEntity.ok().build();
    }




    @PutMapping("/{id}/updatePreference")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> updatePreferences(@PathVariable Long id,@RequestBody UpdatePreferenceRequest request) {
        customerService.updatePreferences(id,request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }












}
