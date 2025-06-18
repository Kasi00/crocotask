package com.example.crocotask.implementations;

import com.example.crocotask.dto.*;
import com.example.crocotask.entity.Role;
import com.example.crocotask.entity.User;
import com.example.crocotask.exception.InvalidInputException;
import com.example.crocotask.exception.NotFoundException;
import com.example.crocotask.service.UserService;
import com.example.crocotask.repository.RoleRepository;
import com.example.crocotask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Value("${jwt.secret-key}")
    private String secretKey;










    @Override
    public void registerAdmin(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already in use");
        }

        Role userRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new NotFoundException("Default role not found"));


        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);

    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user= userRepository.findByEmail(request.getEmail()).orElseThrow(()->new NotFoundException("Invalid Credentials"));
        if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return generateLoginResponse(user);


        }
        throw new InvalidInputException("Invalid login");

    }





    @Override
    public void deleteAdmin(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new NotFoundException("Admin user not found"));
        userRepository.delete(user);
    }

    @Override
    public void updateAdminPassword(Long userId, UpdatePasswordRequest request) {
        User user= userRepository.findById(userId).orElseThrow(()->new NotFoundException("Admin user not found"));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getAllAdmins() {
        return userRepository.findAllAdmins();
    }




    private LoginResponse generateLoginResponse(User user) {
        try{
            JWTClaimsSet claims= new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .claim("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                    .issuer("notification.ge")
                    .expirationTime(new Date(System.currentTimeMillis()+1000L * 60 * 60 * 24 * 7)).build();


            JWSHeader header= new JWSHeader(JWSAlgorithm.HS256);
            SignedJWT signedJWT= new SignedJWT(header, claims);
            signedJWT.sign(new MACSigner(secretKey.getBytes()));

            return new LoginResponse(signedJWT.serialize());
        }
        catch(Exception e){
            throw new InvalidInputException("Failed to generate Token");
        }
    }


}
