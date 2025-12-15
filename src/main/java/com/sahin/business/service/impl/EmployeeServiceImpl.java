package com.sahin.business.service.impl;

import com.sahin.business.dto.EmployeeDto;
import com.sahin.business.entities.Employee;
import com.sahin.business.exception.ResourceNotFoundException;
import com.sahin.business.mapper.EmployeeMapper;
import com.sahin.business.repository.EmployeeRepository;
import com.sahin.business.service.EmployeeService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BCryptPasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.existsByUserName(employeeDto.getUserName())) {
            throw new RuntimeException("Username already exist");
        }
        Employee employee= EmployeeMapper.mapToEmployee(employeeDto);
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
            return EmployeeMapper.maptoEmployeeDto(employeeRepository.save(employee));
    }


    public List<EmployeeDto> getAllEmployee() {
            List<Employee> employees=employeeRepository.findAll();
            return employees.stream().map(EmployeeMapper::maptoEmployeeDto).toList();
    }

    public EmployeeDto getEmployeeById(long employeeId){
            Employee employee=employeeRepository.findById(employeeId).orElseThrow(() ->
                    new ResourceNotFoundException("Employee is not exist:" + employeeId));
            return EmployeeMapper.maptoEmployeeDto(employee);
    }

    public EmployeeDto updateEmployeeById(long employeeId, EmployeeDto updatedEmployee) {
            Employee employee=employeeRepository.findById(employeeId).orElseThrow(() ->
                    new ResourceNotFoundException("Employee is not exist:" + employeeId));
            employee.setUserName(
                    Optional.ofNullable(updatedEmployee.getUserName()).orElse(employee.getUsername())
            );
            employee.setPassword(
                    Optional.ofNullable(updatedEmployee.getPassword()).orElse(employee.getPassword())
            );
            employee.setEmployeeRank(
                    Optional.ofNullable(updatedEmployee.getEmployeeRank()).orElse(employee.getEmployeeRank())
            );
            employeeRepository.save(employee);
            return EmployeeMapper.maptoEmployeeDto(employee);
    }

    public void deleteEmployeeById(long employeeId) {
        employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResourceNotFoundException("Employee is not exist:" + employeeId));
                employeeRepository.deleteById(employeeId);
    }

}
