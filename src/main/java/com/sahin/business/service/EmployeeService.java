package com.sahin.business.service;

import com.sahin.business.dto.EmployeeDto;
import com.sahin.business.entity.Employee;
import com.sahin.business.entity.Task;
import com.sahin.business.exception.ResourceNotFoundException;
import com.sahin.business.mapper.EmployeeMapper;
import com.sahin.business.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }



    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
            Employee employee= EmployeeMapper.mapToEmployee(employeeDto);
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
