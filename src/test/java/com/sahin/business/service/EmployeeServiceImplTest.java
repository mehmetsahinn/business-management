package com.sahin.business.service;

import com.sahin.business.dto.EmployeeDto;
import com.sahin.business.entity.Employee;
import com.sahin.business.exception.ResourceNotFoundException;
import com.sahin.business.repository.EmployeeRepository;
import com.sahin.business.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sahin.business.mapper.EmployeeMapper.maptoEmployeeDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee1;
    private Employee employee2;


    @BeforeEach
    void setUp() {
        employee1 = Employee.builder().id(1).userName("mehmet").password("123").build();
        employee2 = Employee.builder().id(2).userName("sahin").password("123").build();
    }

    @Test
    @DisplayName("Should return all Employees when Employees exist")
    void getAllEmployee_WhenEmployeesExist_ShouldReturnAllEmployee() {
        List<Employee> employeeList = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<EmployeeDto> result = employeeService.getAllEmployee();

        assertEquals(2, result.size());
        assertEquals("mehmet", result.get(0).getUserName());
        assertEquals("sahin", result.get(1).getUserName());
    }

    @Test
    @DisplayName("Should return empty list when No employees exist")
    void getAllEmployee_WhenNoEmployeeExist_ShouldReturnEmptyList() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
        List<EmployeeDto> result = employeeService.getAllEmployee();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should create employee and return employeeDto when valid EmployeeDto given")
    public void createEmployee_WhenValidEmployeeDtoGiven_ShouldCreateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);
        EmployeeDto result = employeeService.createEmployee(maptoEmployeeDto(employee1));

        assertNotNull(result);
        assertEquals(maptoEmployeeDto(employee1).getUserName(), result.getUserName());
        assertEquals(maptoEmployeeDto(employee1).getPassword(), result.getPassword());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should return EmployeeDTO when Employee exist")
    void getEmployeeById_WhenEmployeeExist_ShouldReturnEmployeeDTO() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(employee1));
        EmployeeDto result = employeeService.getEmployeeById(1l);

        assertEquals("mehmet", result.getUserName());
        assertEquals(1, result.getId());
        assertEquals("123", result.getPassword());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return  ResourceNotFoundException when Employee doesn't exist")
    void getEmployeeById_WhenEmployeeDoesNotExist_ShouldThrowResourceNotFoundException() {
        when(employeeRepository.findById(3L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class, () -> employeeService.getEmployeeById(3L));

        assertEquals("Employee is not exist:3", exception.getMessage());
        verify(employeeRepository, times(1)).findById(3L);
    }

    @Test
    @DisplayName("Should update employee and return updated employeeDto when employee exists")
    void updateEmployeeById_WhenEmployeeExists_ShouldUpdateEmployeeById() {
        Employee updatedEmployee = Employee.builder()
                .userName("updateEmployee")
                .password("456")
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));
        EmployeeDto result = employeeService.updateEmployeeById(1L, maptoEmployeeDto(updatedEmployee));

        assertNotNull(result);
        assertEquals("updateEmployee", result.getUserName());
        assertEquals("456", result.getPassword());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when Employee ID does not exist")
    void updateEmployeeById_WhenEmployeeDoesNotExist_ResourceNotFoundException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> employeeService.updateEmployeeById(99L, maptoEmployeeDto(employee1)));

        assertEquals("Employee is not exist:99", exception.getMessage());
        verify(employeeRepository, times(1)).findById(99L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should successfully delete employee when employee exists")
    void deleteEmployeeById_WhenEmployeeExists_ShouldDeleteSuccessfully() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee2));
        doNothing().when(employeeRepository).deleteById(2L);
        employeeService.deleteEmployeeById(2L);
        verify(employeeRepository, times(1)).findById(2L);
        verify(employeeRepository, times(1)).deleteById(2L);
    }

    @Test
    @DisplayName("Should successfully delete employee when employee exists")
    void deleteEmployeeById_WhenEmployeeDoesNotExists_ShouldResourceNotFoundException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> employeeService.deleteEmployeeById(99L));

        assertEquals("Employee is not exist:99", exception.getMessage());
        verify(employeeRepository, times(1)).findById(99L);
        verify(employeeRepository, never()).deleteById(99L);
    }

}
