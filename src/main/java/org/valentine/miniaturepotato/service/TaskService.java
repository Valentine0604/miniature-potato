package org.valentine.miniaturepotato.service;

import org.springframework.stereotype.Service;
import org.valentine.miniaturepotato.dto.TaskResponseDto;
import org.valentine.miniaturepotato.entity.Task;
import org.valentine.miniaturepotato.repository.TaskRepository;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    void completeTask(long taskId);
    List<Task> findIncompleteTasks();
}