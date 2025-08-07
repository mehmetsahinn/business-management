package com.sahin.business.dto;

public class TaskToEmployeeDto {

    private long taskId;
    private long employeeId;

    public TaskToEmployeeDto(long taskId, long employeeId) {
        this.taskId = taskId;
        this.employeeId = employeeId;
    }
    public TaskToEmployeeDto() {
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }


}
