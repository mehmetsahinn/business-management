package com.sahin.business.mapper;

import com.sahin.business.dto.TaskDto;
import com.sahin.business.entities.Task;

public class TaskMapper {
    public static TaskDto maptoTaskDto(Task task){
        return new TaskDto(
                task.getId(),
                task.getTaskName(),
                task.getStatus(),
                task.getDescription(),
                task.getEmployee()
        );
    }

    public static Task mapToTask(TaskDto taskDto){
        return new Task(
                taskDto.getId(),
                taskDto.getTaskName(),
                taskDto.getStatus(),
                taskDto.getDescription(),
                taskDto.getEmployee()
        );
    }




}
