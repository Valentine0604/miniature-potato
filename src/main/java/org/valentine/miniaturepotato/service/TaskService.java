package org.valentine.miniaturepotato.service;


import org.valentine.miniaturepotato.dto.TaskResponseDto;
import org.valentine.miniaturepotato.entity.Task;


import java.util.List;

public interface TaskService {

    Task createTask(Task task);

    void completeTask(long taskId);
    List<Task> findIncompleteTasks();

    TaskResponseDto findByTaskId(long taskId);
}