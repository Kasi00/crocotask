package com.example.crocotask.implementations;

import com.example.crocotask.dto.AddressRequest;
import com.example.crocotask.dto.AddressResponse;
import com.example.crocotask.dto.UpdateAddressRequest;
import com.example.crocotask.entity.Address;
import com.example.crocotask.entity.Customer;
import com.example.crocotask.exception.NotFoundException;
import com.example.crocotask.service.AddressService;
import com.example.crocotask.repository.AddressRepository;
import com.example.crocotask.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;


    @Override
    public void addAddress(Long customerId, AddressRequest request) {
        Customer customer= customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));
        Address address = new Address();
        address.setType(request.getType());
        address.setValue(request.getValue());
        address.setCustomer(customer);
        addressRepository.save(address);
    }

    @Override
    public void updateAddress(Long id, UpdateAddressRequest request) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address not found"));
        address.setValue(request.getNewValue());
        addressRepository.save(address);

    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address not found"));
        addressRepository.delete(address);

    }

    @Override
    public List<AddressResponse> getAddressesByCustomer(Long customerId) {
        return addressRepository.findByCustomerId(customerId).stream()
                .map(addr -> new AddressResponse(addr.getType(), addr.getValue()))
                .collect(Collectors.toList());
    }
}
