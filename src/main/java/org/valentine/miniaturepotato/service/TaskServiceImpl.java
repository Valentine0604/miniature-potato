package org.valentine.miniaturepotato.service;

import org.springframework.stereotype.Service;
import org.valentine.miniaturepotato.entity.Task;
import org.valentine.miniaturepotato.exception.TaskNotFoundException;
import org.valentine.miniaturepotato.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final NotificationService notificationService;

    public TaskServiceImpl(TaskRepository taskRepository,
                           NotificationService notificationService) {
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setCompleted(false);
        notificationService.sendTaskCreationNotification(task);
        return taskRepository.save(task);
    }

    @Override
    public void completeTask(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setCompleted(true);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findIncompleteTasks() {
        return taskRepository.findByCompletedFalse();
    }
}

