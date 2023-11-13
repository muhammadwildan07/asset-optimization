package com.finalproject.assetmanagement.service.implementation;

import com.finalproject.assetmanagement.entity.Role;
import com.finalproject.assetmanagement.entity.constant.ERole;
import com.finalproject.assetmanagement.repository.RoleRepository;
import com.finalproject.assetmanagement.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImplementation implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Role getOrSave(ERole role) {
        return roleRepository.findByRole(role).orElseGet(()-> roleRepository.save(Role.builder()
                        .role(role)
                .build())
        );
    }
}
