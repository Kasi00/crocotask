package com.example.crocotask.service;

import com.example.crocotask.dto.*;
import com.example.crocotask.exception.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    void registerAdmin(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void deleteAdmin(Long userId);
    void updateAdminPassword(Long userId, UpdatePasswordRequest request);
    List<UserResponse> getAllAdmins();








}
