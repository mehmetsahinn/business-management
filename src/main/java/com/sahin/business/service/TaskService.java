package com.sahin.business.service;

import com.sahin.business.dto.TaskDto;
import com.sahin.business.dto.TaskToEmployeeDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);
    List<TaskDto> getAllTasks();
    TaskDto getTaskById(long taskId);
    TaskDto updateTaskById(long taskId, TaskDto updatedTask);
    void deleteTaskById(long taskId);
    TaskDto assignTask(TaskToEmployeeDto taskToEmployeeDto);

}
