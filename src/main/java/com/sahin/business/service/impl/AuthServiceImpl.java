package com.sahin.business.service.impl;

import com.sahin.business.dto.AuthRequest;
import com.sahin.business.dto.AuthResponse;
import com.sahin.business.entity.Employee;
import com.sahin.business.repository.EmployeeRepository;
import com.sahin.business.security.AuthEntryPoint;
import com.sahin.business.security.JwtService;
import com.sahin.business.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Override
    public AuthResponse login(AuthRequest request) {
        try {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            authenticationProvider.authenticate(auth);

            Optional<Employee> optionalEmployee = employeeRepository.findByUserName(request.getUsername());
            String accessToken = jwtService.generateToken(optionalEmployee.get());

            return new AuthResponse(accessToken);
        } catch (Exception e) {
            System.out.println("Kullanıcı adı veya şifre hatalı");
        }
        return null;
    }
}
