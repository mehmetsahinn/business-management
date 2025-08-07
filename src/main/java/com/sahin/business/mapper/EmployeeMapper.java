package com.sahin.business.mapper;

import com.sahin.business.dto.EmployeeDto;
import com.sahin.business.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto maptoEmployeeDto(Employee employee){
        return new EmployeeDto(
                employee.getId(),
                employee.getUsername(),
                employee.getPassword(),
                employee.getEmployeeRank()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto){
        return new Employee(
                employeeDto.getId(),
                employeeDto.getUserName(),
                employeeDto.getPassword(),
                employeeDto.getEmployeeRank()
        );
    }


}
