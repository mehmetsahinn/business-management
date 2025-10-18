package com.sahin.business.controller;

import com.sahin.business.dto.*;
import com.sahin.business.dto.TaskToEmployeeDto;
import com.sahin.business.service.impl.TaskServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {
    private final TaskServiceImpl taskServiceImpl;

    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto){
        return new ResponseEntity<>(taskServiceImpl.createTask(taskDto), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<TaskDto>>getAllTasks(){
        List <TaskDto> tasks= taskServiceImpl.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("{id}")
    public ResponseEntity<TaskDto>getTaskById(@PathVariable("id")long taskId){
        TaskDto taskDto= taskServiceImpl.getTaskById(taskId);
        return ResponseEntity.ok(taskDto);
    }
    @PutMapping("{id}")
    public ResponseEntity<TaskDto>UpdateTaskById(@PathVariable("id")long taskId,@RequestBody TaskDto updatedTask){
        TaskDto taskDto= taskServiceImpl.updateTaskById(taskId, updatedTask);
        return ResponseEntity.ok(taskDto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String>deleteTaskById(@PathVariable("id")long taskId){
        taskServiceImpl.deleteTaskById(taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }
   @PutMapping("/appointed")
    public ResponseEntity<TaskDto>taskToEmployee(@RequestBody TaskToEmployeeDto taskToEmployeeDTO ){
       TaskDto taskDto= taskServiceImpl.assignTask(taskToEmployeeDTO);
       return ResponseEntity.ok(taskDto);
   }

}
