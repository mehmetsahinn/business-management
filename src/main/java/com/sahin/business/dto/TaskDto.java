package com.sahin.business.dto;

import com.sahin.business.entities.Employee;
import com.sahin.business.entities.Statuses;


public class TaskDto {

    private long id;
    private String taskName;
    private Statuses status;
    private String description;
    private Employee employee;

    public TaskDto(long id, String taskName, Statuses status, String description,Employee employee) {
        this.id = id;
        this.taskName = taskName;
        this.status = status;
        this.description = description;
        this.employee = employee;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


}

