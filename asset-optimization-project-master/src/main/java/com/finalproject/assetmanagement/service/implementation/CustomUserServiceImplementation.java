package com.finalproject.assetmanagement.service.implementation;

import com.finalproject.assetmanagement.entity.UserCredential;
import com.finalproject.assetmanagement.entity.UserDetailImplementation;
import com.finalproject.assetmanagement.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImplementation implements UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found"));
        List<SimpleGrantedAuthority> grantedAuthorities = userCredential.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name())).collect(Collectors.toList());


        return UserDetailImplementation.builder()
                .email(userCredential.getEmail())
                .password(userCredential.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }
}
