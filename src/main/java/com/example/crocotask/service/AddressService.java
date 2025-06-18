package com.example.crocotask.service;

import com.example.crocotask.dto.AddressRequest;
import com.example.crocotask.dto.AddressResponse;
import com.example.crocotask.dto.UpdateAddressRequest;

import java.util.List;

public interface AddressService {
    void addAddress(Long customerId, AddressRequest request);
    void updateAddress(Long id, UpdateAddressRequest request);
    void deleteAddress(Long id);
    List<AddressResponse> getAddressesByCustomer(Long customerId);
}
