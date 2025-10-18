package com.sahin.business.service.impl;

import com.sahin.business.dto.*;
import com.sahin.business.entity.Employee;
import com.sahin.business.entity.Task;
import com.sahin.business.exception.ResourceNotFoundException;
import com.sahin.business.mapper.EmployeeMapper;
import com.sahin.business.mapper.TaskMapper;
import com.sahin.business.repository.TaskRepository;
import com.sahin.business.service.EmployeeService;
import com.sahin.business.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private EmployeeService employeeService;

    public TaskServiceImpl(TaskRepository taskRepository, EmployeeService employeeService) {
        this.taskRepository = taskRepository;
        this.employeeService = employeeService;
    }


    public TaskDto createTask(TaskDto taskDto) {
            Task task = TaskMapper.mapToTask(taskDto);
            return TaskMapper.maptoTaskDto(taskRepository.save(task));
    }


    public List<TaskDto> getAllTasks() {
            List<Task> tasks = taskRepository.findAll();
            return tasks.stream().map(TaskMapper::maptoTaskDto).toList();

    }

    public TaskDto getTaskById(long taskId) {
            Task task = taskRepository.findById(taskId).orElseThrow(() ->
                    new ResourceNotFoundException("Task is not exist:" + taskId));
            return TaskMapper.maptoTaskDto(task);
    }

    public TaskDto updateTaskById(long taskId, TaskDto updatedTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task is not exist: " + taskId));

        task.setTaskName(
                Optional.ofNullable(updatedTask.getTaskName()).orElse(task.getTaskName()));
        task.setStatus(
                Optional.ofNullable(updatedTask.getStatus()).orElse(task.getStatus()));
        task.setDescription(
                Optional.ofNullable(updatedTask.getDescription()).orElse(task.getDescription()));

        Task updatedTaskObj = taskRepository.save(task);
        return TaskMapper.maptoTaskDto(updatedTaskObj);
    }

    public void deleteTaskById(long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new ResourceNotFoundException("Task is not exist:" + taskId));
                taskRepository.deleteById(taskId);
    }

    public TaskDto assignTask(TaskToEmployeeDto taskToEmployeeDto) {
        TaskDto assignedTaskDto = getTaskById(taskToEmployeeDto.getTaskId());
        EmployeeDto assignedEmployeeDto = employeeService.getEmployeeById(taskToEmployeeDto.getEmployeeId());
        Employee assignedEmployee = EmployeeMapper.mapToEmployee(assignedEmployeeDto);
        Task assignedTask = TaskMapper.mapToTask(assignedTaskDto);
        assignedTask.setEmployee(assignedEmployee);
        Task savedTask = taskRepository.save(assignedTask);
        return TaskMapper.maptoTaskDto(savedTask);
    }

}
