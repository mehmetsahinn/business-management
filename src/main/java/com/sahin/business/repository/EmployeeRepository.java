package com.sahin.business.repository;

import com.sahin.business.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository  extends JpaRepository<Employee, Long> {

}
