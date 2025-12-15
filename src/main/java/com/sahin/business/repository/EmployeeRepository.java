package com.sahin.business.repository;

import com.sahin.business.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository  extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUserName(String userName);
    Boolean existsByUserName (String userName);
}
