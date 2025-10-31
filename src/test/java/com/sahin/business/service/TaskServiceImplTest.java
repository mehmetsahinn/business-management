package com.sahin.business.service;

import com.sahin.business.dto.TaskDto;
import com.sahin.business.entity.Task;
import com.sahin.business.exception.ResourceNotFoundException;
import com.sahin.business.mapper.TaskMapper;
import com.sahin.business.repository.TaskRepository;
import com.sahin.business.service.impl.EmployeeServiceImpl;
import com.sahin.business.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sahin.business.mapper.TaskMapper.maptoTaskDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    private TaskService taskService;
    private EmployeeService employeeService;
    private Task sampleTask1;
    private Task sampleTask2;
    private Task sampleTask3;
    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(taskRepository, employeeService);
        sampleTask1 = Task.builder()
                .id(1L)
                .taskName("Task 1")
                .description("Desc 1")
                .build();
        sampleTask2 = Task.builder()
                .id(2L)
                .taskName("Task 2")
                .description("Desc 2")
                .build();
    }

    @Test
    @DisplayName("Should return all tasks when tasks exist")
    void should_ReturnAllTasks_When_TasksExist(){
        List<Task> taskList = Arrays.asList(sampleTask1, sampleTask2);
        when(taskRepository.findAll()).thenReturn(taskList);
        List<TaskDto> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTaskName());
        assertEquals("Task 2", result.get(1).getTaskName());
    }
    @Test
    @DisplayName("Should return empty List when NoTasksExist")
    public void should_ReturnEmptyList_When_NoTasksExist(){
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        List<TaskDto>result = taskService.getAllTasks();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return TaskDto when task exists")
    public void should_CreateTask_When_ValidTaskDtoGiven() {
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask1);
        TaskDto result = taskService.createTask(maptoTaskDto(sampleTask1));

        assertNotNull(result);
        assertEquals(maptoTaskDto(sampleTask1).getTaskName(), result.getTaskName());
        assertEquals(maptoTaskDto(sampleTask1).getDescription(), result.getDescription());
        verify(taskRepository, times(1)).save(any(Task.class));
    }
    @Test
    @DisplayName("Should return TaskDto when task exists")
    void should_ReturnTaskDto_When_TaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask1));
        TaskDto result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(sampleTask1.getTaskName(), result.getTaskName());
        assertEquals(sampleTask1.getDescription(), result.getDescription());
        verify(taskRepository, times(1)).findById(1L);
    }
    @Test
    @DisplayName("Should throw ResourceNotFoundException when task does not exist")
    void should_ThrowResourceNotFoundException_When_TaskDoesNotExist() {
        when(taskRepository.findById(3L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class, () -> taskService.getTaskById(3L));

        assertEquals("Task is not exist:3", exception.getMessage());
        verify(taskRepository, times(1)).findById(3L);
    }





}




