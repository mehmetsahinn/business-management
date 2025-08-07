package com.sahin.business.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "taskName_db")
    private String taskName;
    @Column(name = "status_db")
    private Statuses status;
    @Column(name = "description_db")
    private String description=null;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = true)
    private Employee employee;

    @PrePersist
    public void setDefaultValues() {
        if (status == null) {
            status = Statuses.OPEN;
        }
    }

    public Task(long id, String taskName, Statuses status, String description,Employee employee) {
        this.id = id;
        this.taskName = taskName;
        this.status = status;
        this.description = description;
        this.employee = employee;
    }

    public Task() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setStatus(Statuses status) {this.status = status;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmployee(Employee employee) {this.employee = employee;}


    public long getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public Statuses getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Employee getEmployee() {return employee;}
}
