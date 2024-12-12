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

    /**
     * Verifies that the createTask method saves a task to the database and sends a task creation notification.
     *
     * @see TaskServiceImpl#createTask(Task)
     */
    @Test
    void createTask_ShouldSaveAndNotify() {
        Task inputTask = new Task(1L, "Test Task", "Description",
                LocalDateTime.now(), LocalDateTime.of(2024, 12, 31, 12, 0),
                Priority.HIGH, false);
        Task savedTask = new Task(2L, "Test Task", "Description",
                LocalDateTime.now(), LocalDateTime.of(2024, 12, 31, 12, 0),
                Priority.HIGH, false);

        when(taskRepository.save(inputTask)).thenReturn(savedTask);

        Task result = taskService.createTask(inputTask);

        assertNotNull(result);
        assertEquals(2L, result.getId());

        verify(taskRepository, times(1)).save(inputTask);

        verify(notificationService, times(1)).sendTaskCreationNotification(inputTask);
    }

    /**
     * Verifies that the completeTask method marks a task as completed.
     *
     * @see TaskServiceImpl#completeTask(long)
     */
    @Test
    void completeTask_ShouldMarkAsCompleted() {
        Task incompleteTask = new Task(1L, "Test Task", "Description",
                LocalDateTime.now(), LocalDateTime.of(2024, 12, 31, 12, 0),
                Priority.MEDIUM, false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(incompleteTask));
        when(taskRepository.save(any(Task.class))).thenReturn(incompleteTask);

        taskService.completeTask(1L);

        assertTrue(incompleteTask.isCompleted());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(incompleteTask);
    }

    /**
     * Verifies that the completeTask method throws a TaskNotFoundException
     * when attempting to complete a task that does not exist.
     *
     * @see TaskServiceImpl#completeTask(long)
     */
    @Test
    void completeTask_TaskNotFound_ShouldThrowException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.completeTask(1L));
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).save(any());
    }

    /**
     * Verifies that the findIncompleteTasks method returns a list of incomplete tasks.
     *
     * @see TaskServiceImpl#findIncompleteTasks()
     */
    @Test
    void findIncompleteTasks_ShouldReturnIncompleteTasks() {
        Task task1 = new Task(1L, "Task 1", "Description 1",
                LocalDateTime.now(), LocalDateTime.of(2024, 12, 31, 12, 0),
                Priority.LOW, false);
        Task task2 = new Task(2L, "Task 2", "Description 2",
                LocalDateTime.now(), LocalDateTime.of(2024, 11, 30, 12, 0),
                Priority.HIGH, false);

        when(taskRepository.findByCompletedFalse()).thenReturn(List.of(task1, task2));

        List<Task> tasks = taskService.findIncompleteTasks();

        assertEquals(2, tasks.size());
        assertFalse(tasks.get(0).isCompleted());
        verify(taskRepository, times(1)).findByCompletedFalse();
    }

    /**
     * Verifies that the findByTaskId method returns a TaskResponseDto containing the task's
     * details when the task exists.
     *
     * @see TaskServiceImpl#findByTaskId(long)
     */
    @Test
    void findByTaskId_ShouldReturnTaskResponseDto() {
        Task task = new Task(1L, "Task 1", "Description 1",
                LocalDateTime.now(), LocalDateTime.of(2024, 12, 31, 12, 0),
                Priority.MEDIUM, false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponseDto result = taskService.findByTaskId(1L);

        assertNotNull(result);
        assertEquals("Task 1", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    /**
     * Verifies that the findByTaskId method throws a TaskNotFoundException
     * when attempting to find a task with a non-existent ID.
     *
     * @see TaskServiceImpl#findByTaskId(long)
     */
    @Test
    void findByTaskId_TaskNotFound_ShouldThrowException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.findByTaskId(1L));
        verify(taskRepository, times(1)).findById(1L);
    }
}