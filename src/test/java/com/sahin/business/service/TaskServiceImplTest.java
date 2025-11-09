package com.sahin.business.service;

import com.sahin.business.dto.EmployeeDto;
import com.sahin.business.dto.TaskDto;
import com.sahin.business.dto.TaskToEmployeeDto;
import com.sahin.business.entity.Employee;
import com.sahin.business.entity.Statuses;
import com.sahin.business.entity.Task;
import com.sahin.business.exception.ResourceNotFoundException;
import com.sahin.business.mapper.EmployeeMapper;
import com.sahin.business.mapper.TaskMapper;
import com.sahin.business.repository.TaskRepository;
import com.sahin.business.service.impl.TaskServiceImpl;
import com.sahin.business.dto.TaskToEmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sahin.business.mapper.TaskMapper.maptoTaskDto;
import static com.sahin.business.mapper.EmployeeMapper.maptoEmployeeDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private TaskServiceImpl taskService;

    private Task sampleTask1;
    private Task sampleTask2;


    @BeforeEach
    void setUp() {
        sampleTask1 = Task.builder()
                .id(1L)
                .taskName("Task 1")
                .description("Desc 1")
                .build();
        sampleTask2 = Task.builder()
                .id(    2L)
                .taskName("Task 2")
                .description("Desc 2")
                .build();
    }

    @Test
    @DisplayName("Should return all tasks when tasks exist")
    void getAllTasks_WhenTasksExist_ShouldReturnAllTasks(){
        List<Task> taskList = Arrays.asList(sampleTask1, sampleTask2);
        when(taskRepository.findAll()).thenReturn(taskList);
        List<TaskDto> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTaskName());
        assertEquals("Task 2", result.get(1).getTaskName());
    }
    @Test
    @DisplayName("Should return empty List when NoTasksExist")
    public void getAllTasks_WhenNoTasksExist_ShouldReturnEmptyList(){
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        List<TaskDto>result = taskService.getAllTasks();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should create task and return TaskDto when valid taskDto given")
    public void createTask_WhenValidTaskDtoGiven_ShouldCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask1);
        TaskDto result = taskService.createTask(maptoTaskDto(sampleTask1));

        assertNotNull(result);
        assertEquals(maptoTaskDto(sampleTask1).getTaskName(), result.getTaskName());
        assertEquals(maptoTaskDto(sampleTask1).getDescription(), result.getDescription());
        verify(taskRepository, times(1)).save(any(Task.class));
    }
    @Test
    @DisplayName("Should return TaskDto when task exists")
    void getTaskById_WhenTaskExists_ShouldReturnTaskDto() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask1));
        TaskDto result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(sampleTask1.getTaskName(), result.getTaskName());
        assertEquals(sampleTask1.getDescription(), result.getDescription());
        verify(taskRepository, times(1)).findById(1L);
    }
    @Test
    @DisplayName("Should throw ResourceNotFoundException when task does not exist")
    void getTaskById_WhenTaskDoesNotExist_ShouldThrowResourceNotFoundException() {
        when(taskRepository.findById(3L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class, () -> taskService.getTaskById(3L));

        assertEquals("Task is not exist:3", exception.getMessage());
        verify(taskRepository, times(1)).findById(3L);
    }

    @Test
    @DisplayName("Should update task and return updated TaskDto when task exists")
    void updateTaskById_WhenTaskExists_ShouldUpdateTaskById(){
        Task updatedTask = Task.builder()
                .taskName("Updated Task 1")
                .description("Updated Desc 1")
                .status(Statuses.IN_DEVELOPMENT)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask1));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        TaskDto result = taskService.updateTaskById(1L, maptoTaskDto(updatedTask));

        assertNotNull(result);
        assertEquals("Updated Task 1", result.getTaskName());
        assertEquals("Updated Desc 1", result.getDescription());
        assertEquals(Statuses.IN_DEVELOPMENT, result.getStatus());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when task ID does not exist")
    void updateTaskById_WhenTaskDoesNotExist_ShouldThrowException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> taskService.updateTaskById(99L, maptoTaskDto(sampleTask1)));

        assertEquals("Task is not exist: " + 99L, exception.getMessage());
        verify(taskRepository, times(1)).findById(99L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should successfully delete task when task exists")
    void deleteTaskById_WhenTaskExists_ShouldDeleteSuccessfully() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask1));
        doNothing().when(taskRepository).delete(sampleTask1);
        taskService.deleteTaskById(1L);
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(sampleTask1);
    }
    @Test
    @DisplayName("Should throw ResourceNotFoundException when task does not exist")
    void deleteTaskById_WhenTaskDoesNotExist_ShouldThrowException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.deleteTaskById(99L));
        assertEquals("Task not found with id: " + 99L, exception.getMessage());
        verify(taskRepository, times(1)).findById(99L);
        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    @DisplayName("Should assign employee to task successfully")
    void assignTask_WhenBothExist_ShouldReturnTask() {

        TaskToEmployeeDto taskToEmployeeDto = new TaskToEmployeeDto();
        taskToEmployeeDto.setTaskId(1L);
        taskToEmployeeDto.setEmployeeId(10L);

        Employee employee = Employee.builder()
                .id(10L)
                .userName("mehmet")
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask1));
        when(employeeService.getEmployeeById(10L)).thenReturn(EmployeeMapper.maptoEmployeeDto(employee));
        sampleTask1.setEmployee(employee);
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask1);
        TaskDto result = taskService.assignTask(taskToEmployeeDto);

        assertNotNull(result);
        assertEquals(sampleTask1.getId(), result.getId());

        verify(taskRepository, times(1)).save(any(Task.class));
        verify(employeeService, times(1)).getEmployeeById(10L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when employee not found")
    void assignTask_WhenEmployeeNotFound_ShouldThrowException() {
        TaskToEmployeeDto taskToEmployeeDto = new TaskToEmployeeDto();
        taskToEmployeeDto.setTaskId(1L);
        taskToEmployeeDto.setEmployeeId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask1));
        when(employeeService.getEmployeeById(1L))
                .thenThrow(new ResourceNotFoundException("Employee not found with id: 1"));

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.assignTask(taskToEmployeeDto));

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
    }

}




