package org.valentine.miniaturepotato.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.valentine.miniaturepotato.dto.TaskResponseDto;
import org.valentine.miniaturepotato.entity.Priority;
import org.valentine.miniaturepotato.entity.Task;
import org.valentine.miniaturepotato.exception.TaskNotFoundException;
import org.valentine.miniaturepotato.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final NotificationService notificationService;


    @Override
    public Task createTask(Task task) {
        task.setCompleted(false);
        notificationService.sendTaskCreationNotification(task);
        Task savedTask = taskRepository.save(task);

        return savedTask;
    }

    @Override
    public void completeTask(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setCompleted(true);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findIncompleteTasks() {
        return taskRepository.findByCompletedFalse();
    }

    public TaskResponseDto findByTaskId(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        return TaskResponseDto.fromEntity(task);
    }


}

