package org.valentine.miniaturepotato.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.valentine.miniaturepotato.dto.TaskResponseDto;
import org.valentine.miniaturepotato.entity.Priority;
import org.valentine.miniaturepotato.entity.Task;
import org.valentine.miniaturepotato.exception.TaskNotFoundException;
import org.valentine.miniaturepotato.repository.TaskRepository;
import org.valentine.miniaturepotato.service.NotificationService;
import org.valentine.miniaturepotato.service.TaskServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_ShouldSaveAndNotify() {
        // Arrange
        Task inputTask = new Task(1L, "Test Task", "Description",
                LocalDateTime.now(), LocalDateTime.of(2024, 12, 31, 12, 0),
                Priority.HIGH, false);
        Task savedTask = new Task(2L, "Test Task", "Description",
                LocalDateTime.now(), LocalDateTime.of(2024, 12, 31, 12, 0),
                Priority.HIGH, false);

        when(taskRepository.save(inputTask)).thenReturn(savedTask);

        // Act
        Task result = taskService.createTask(inputTask);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());

        // Ensure repository save is called exactly once
        verify(taskRepository, times(1)).save(inputTask);

        // Ensure notification is sent exactly once
        verify(notificationService, times(1)).sendTaskCreationNotification(inputTask);
    }

    @Test
    void completeTask_ShouldMarkAsCompleted() {
        // Arrange
        Task incompleteTask = new Task(1L, "Test Task", "Description",
                LocalDateTime.now(), LocalDateTime.of(2024, 12, 31, 12, 0),
                Priority.MEDIUM, false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(incompleteTask));
        when(taskRepository.save(any(Task.class))).thenReturn(incompleteTask);

        // Act
        taskService.completeTask(1L);

        // Assert
        assertTrue(incompleteTask.isCompleted());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(incompleteTask);
    }

    @Test
    void completeTask_TaskNotFound_ShouldThrowException() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.completeTask(1L));
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void findIncompleteTasks_ShouldReturnIncompleteTasks() {
        // Arrange
        Task task1 = new Task(1L, "Task 1", "Description 1",
                LocalDateTime.now(), LocalDateTime.of(2024, 12, 31, 12, 0),
                Priority.LOW, false);
        Task task2 = new Task(2L, "Task 2", "Description 2",
                LocalDateTime.now(), LocalDateTime.of(2024, 11, 30, 12, 0),
                Priority.HIGH, false);

        when(taskRepository.findByCompletedFalse()).thenReturn(List.of(task1, task2));

        // Act
        List<Task> tasks = taskService.findIncompleteTasks();

        // Assert
        assertEquals(2, tasks.size());
        assertFalse(tasks.get(0).isCompleted());
        verify(taskRepository, times(1)).findByCompletedFalse();
    }

    @Test
    void findByTaskId_ShouldReturnTaskResponseDto() {
        // Arrange
        Task task = new Task(1L, "Task 1", "Description 1",
                LocalDateTime.now(), LocalDateTime.of(2024, 12, 31, 12, 0),
                Priority.MEDIUM, false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act
        TaskResponseDto result = taskService.findByTaskId(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Task 1", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void findByTaskId_TaskNotFound_ShouldThrowException() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.findByTaskId(1L));
        verify(taskRepository, times(1)).findById(1L);
    }
}