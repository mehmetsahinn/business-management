package com.sahin.business.controller;

import com.sahin.business.dto.EmployeeDto;
import com.sahin.business.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employee")

public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto>createEmployee(@RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<EmployeeDto>>getAllEmployee(){
        List<EmployeeDto> employees=employeeService.getAllEmployee();
                return ResponseEntity.ok(employees);
    }
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto>getEmployeeById(@PathVariable("id")long employeeId){
        EmployeeDto employee=employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto>updateEmployeeById(@PathVariable("id")long employeeId,@RequestBody EmployeeDto updatedEmployee){
        EmployeeDto employee=employeeService.updateEmployeeById(employeeId,updatedEmployee);
        return ResponseEntity.ok(employee);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id")long employeeId){
        employeeService.deleteEmployeeById(employeeId);
        return ResponseEntity.ok("Employee deleted successfully");
    }

}
