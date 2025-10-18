package com.sahin.business.service;

import com.sahin.business.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployee();
    EmployeeDto getEmployeeById(long employeeId);
    EmployeeDto updateEmployeeById(long employeeId, EmployeeDto updatedEmployee);
    void deleteEmployeeById(long employeeId);
}
