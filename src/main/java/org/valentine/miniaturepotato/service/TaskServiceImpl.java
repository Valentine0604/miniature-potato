package org.valentine.miniaturepotato.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.valentine.miniaturepotato.dto.TaskResponseDto;
import org.valentine.miniaturepotato.entity.Task;
import org.valentine.miniaturepotato.exception.TaskNotFoundException;
import org.valentine.miniaturepotato.repository.TaskRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final NotificationService notificationService;


    /**
     * Creates a new task.
     *
     * @param task the task to be created
     * @return the created task
     */
    @Override
    public Task createTask(Task task) {
        task.setCompleted(false);
        notificationService.sendTaskCreationNotification(task);

        return taskRepository.save(task);
    }

    /**
     * Marks a task as completed.
     *
     * @param taskId the ID of the task to mark as completed
     * @throws TaskNotFoundException if a task with the given ID does not exist
     */
    @Override
    public void completeTask(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        task.complete();
        taskRepository.save(task);
    }


    /**
     * Retrieves a list of incomplete tasks.
     *
     * @return a list of tasks that have not been completed
     * @throws TaskNotFoundException if no incomplete tasks exist
     */
    @Override
    public List<Task> findIncompleteTasks() {
        List <Task> incompleteTasks = taskRepository.findByCompletedFalse();
        if (incompleteTasks.isEmpty()) throw new TaskNotFoundException();
        return incompleteTasks;
    }

    /**
     * Finds a task by its ID.
     *
     * @param taskId the ID of the task to find
     * @return the found task, wrapped in a TaskResponseDto
     * @throws TaskNotFoundException if no task with the given ID exists
     */
    public TaskResponseDto findByTaskId(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        return TaskResponseDto.fromEntity(task);
    }

    /**
     * Retrieves all tasks.
     *
     * @return a list of all tasks
     * @throws TaskNotFoundException if no tasks exist
     */
    @Override
    public List<Task> findAllTasks() {
        List <Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) throw new TaskNotFoundException();
        return tasks;
    }


}

