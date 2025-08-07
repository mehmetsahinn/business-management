package com.sahin.business.dto;


import com.sahin.business.entity.EmployeeRank;

public class EmployeeDto {


    private long id;
    private String userName;
    private String password;
    private EmployeeRank employeeRank;

    public EmployeeDto(long id, String userName, String password, EmployeeRank employeeRank) {
        this.id = id;
        this.userName = userName;
        this.password=password;
        this.employeeRank=employeeRank;

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String employeeName) {
        this.userName = employeeName;
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
