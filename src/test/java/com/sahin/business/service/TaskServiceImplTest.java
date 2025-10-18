package com.sahin.business.service;

import com.sahin.business.dto.TaskDto;
import com.sahin.business.entity.Task;
import com.sahin.business.repository.TaskRepository;
import com.sahin.business.service.impl.EmployeeServiceImpl;
import com.sahin.business.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskServiceImplTest {
    private TaskService taskService;
    private TaskRepository taskRepository;
    private EmployeeService employeeService;
    private Task sampleTask1;
    private Task sampleTask2;
    private Task sampleTask3;

    @BeforeEach
    void setUp() {
        taskRepository = Mockito.mock(TaskRepository.class);
        taskService = new TaskServiceImpl(taskRepository, employeeService);
        sampleTask1 = Task.builder().id(1L).taskName("Task 1").description("Desc 1").build();
        sampleTask2 = Task.builder().id(2L).taskName("Task 2").description("Desc 2").build();
    }

    @Test
    void should_ReturnAllTasks_When_TasksExist(){
        List<Task> taskList = Arrays.asList(sampleTask1, sampleTask2);
        when(taskRepository.findAll()).thenReturn(taskList);

        List<TaskDto> result = taskService.getAllTasks();
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTaskName());
        assertEquals("Task 2", result.get(1).getTaskName());
    }

}




