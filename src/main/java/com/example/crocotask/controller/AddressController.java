package com.example.crocotask.controller;

import com.example.crocotask.dto.AddressRequest;
import com.example.crocotask.dto.AddressResponse;
import com.example.crocotask.dto.UpdateAddressRequest;
import com.example.crocotask.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.crocotask.security.AuthorizationConstants.ADMIN;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;






    @GetMapping("/{customerId}")
    @PreAuthorize(ADMIN)
    public ResponseEntity<List<AddressResponse>> getAddressesByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(addressService.getAddressesByCustomer(customerId));
    }

    @PostMapping("/{customerId}/add")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> addAddress(@PathVariable Long customerId, @RequestBody AddressRequest request) {
        addressService.addAddress(customerId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/update")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> updateAddress(@PathVariable Long id, @RequestBody UpdateAddressRequest request) {
        addressService.updateAddress(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }



}
