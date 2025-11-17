package com.sahin.business.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
@AllArgsConstructor
@Entity
@Builder
@Table(name="Employees")
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "userName_db")
    private String userName;
    @Column(name = "password_db")
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @Column(name = "Employee_Rank_db")
    private EmployeeRank employeeRank;
    @PrePersist
    public void setDefaultValues() {
        if (employeeRank == null) {
            employeeRank =EmployeeRank.EMPLOYEE;
        }
    }
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Task> tasks;



    public Employee(long id, String employeeName, String password, EmployeeRank employeeRank) {
        this.id = id;
        this.userName=employeeName;
        this.password=password;
        this.employeeRank=employeeRank;
    }

    public Employee() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeRank getEmployeeRank() {
        return employeeRank;
    }

    public void setEmployeeRank(EmployeeRank employeeRank) {
        this.employeeRank = employeeRank;
    }
}
