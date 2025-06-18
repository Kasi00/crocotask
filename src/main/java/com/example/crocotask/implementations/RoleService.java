package com.example.crocotask.implementations;

import com.example.crocotask.entity.Role;
import com.example.crocotask.exception.NotFoundException;
import com.example.crocotask.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService{
    private final RoleRepository roleRepository;


    public Role getRole(Long id) {
       return roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role with id " + id + " not found"));

    }
}
