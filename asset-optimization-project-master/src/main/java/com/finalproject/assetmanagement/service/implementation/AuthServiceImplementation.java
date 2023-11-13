package com.finalproject.assetmanagement.service.implementation;

import com.finalproject.assetmanagement.entity.Role;
import com.finalproject.assetmanagement.entity.UserCredential;
import com.finalproject.assetmanagement.entity.UserDetailImplementation;
import com.finalproject.assetmanagement.entity.constant.ERole;
import com.finalproject.assetmanagement.model.request.AuthRequest;
import com.finalproject.assetmanagement.model.request.EmployeeRequest;
import com.finalproject.assetmanagement.model.request.ManagerRequest;
import com.finalproject.assetmanagement.model.response.LoginResponse;
import com.finalproject.assetmanagement.model.response.RegisterResponse;
import com.finalproject.assetmanagement.repository.EmployeeRepository;
import com.finalproject.assetmanagement.repository.UserCredentialRepository;
import com.finalproject.assetmanagement.security.BCryptUtils;
import com.finalproject.assetmanagement.security.JwtUtils;
import com.finalproject.assetmanagement.service.AuthService;
import com.finalproject.assetmanagement.service.EmployeeService;
import com.finalproject.assetmanagement.service.ManagerService;
import com.finalproject.assetmanagement.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptUtils bCryptUtils;
    private final EmployeeService employeeService;
    private final ManagerService managerService;
    private final EmployeeRepository employeeRepository;
    private final JwtUtils jwtUtils;
    private final RoleService roleService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerEmployee(AuthRequest request) {
        try {
            Role role = roleService.getOrSave(ERole.ROLE_EMPLOYEE);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);
            EmployeeRequest employee = EmployeeRequest.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .email(request.getEmail())
                    .mobilePhone(request.getMobilePhone())
                    .userCredential(userCredential)
                    .build();
//            employeeRepository.save(employee);
            employeeService.createNewEmployee(employee);
            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .build();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "employee already exist");
        }
    }
    @Override
    public RegisterResponse registerManager(AuthRequest request) {
        try {
            Role role = roleService.getOrSave(ERole.ROLE_MANAGER);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);
            ManagerRequest manager = ManagerRequest.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .email(request.getEmail())
                    .mobilePhone(request.getMobilePhone())
                    .userCredential(userCredential)
                    .build();
            managerService.createNewManager(manager);
            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .build();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "manager already exist");
        }
    }

    @Override
    public LoginResponse login(AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailImplementation userDetail = (UserDetailImplementation) authentication.getPrincipal();

        List<String > roles = userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = jwtUtils.generateToken(userDetail.getEmail());

        return LoginResponse.builder()
                .email(request.getEmail())
                .Token(token)
                .roles(roles)
                .build();
    }


}
