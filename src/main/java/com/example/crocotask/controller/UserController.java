package com.example.crocotask.controller;

import com.example.crocotask.dto.*;
import com.example.crocotask.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.crocotask.security.AuthorizationConstants.ADMIN;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;



    @GetMapping("/all")
    @PreAuthorize(ADMIN)
    public ResponseEntity<List<UserResponse>> getAllAdmins() {
        return ResponseEntity.ok(userService.getAllAdmins());
    }




    @PostMapping("/register")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        userService.registerAdmin(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }


    @PutMapping("/{userId}/password")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> updatePassword(@PathVariable Long userId,@Valid @RequestBody UpdatePasswordRequest request) {
        userService.updateAdminPassword(userId, request);
        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/{userId}/delete")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long userId) {
        userService.deleteAdmin(userId);
        return ResponseEntity.ok().build();
    }



}
