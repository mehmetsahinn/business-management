package com.sahin.business.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@Entity
@Builder
@Table(name="Employees")
public class Employee  implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "userName_db", unique = true, nullable = false)
    private String userName;
    @Column(name = "password_db", nullable = false)
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @Column(name = "Employee_Rank_db")
    private Role employeeRank;
    @PrePersist
    public void setDefaultValues() {
        if (employeeRank == null) {
            employeeRank = Role.EMPLOYEE;
        }
    }
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Task> tasks;



    public Employee(long id, String employeeName, String password, Role employeeRank) {
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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getEmployeeRank() {
        return employeeRank;
    }

    public void setEmployeeRank(Role employeeRank) {
        this.employeeRank = employeeRank;
    }
}
