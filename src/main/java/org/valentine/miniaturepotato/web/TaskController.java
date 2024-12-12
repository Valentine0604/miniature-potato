package org.valentine.miniaturepotato.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.valentine.miniaturepotato.dto.TaskCreateDto;
import org.valentine.miniaturepotato.dto.TaskResponseDto;
import org.valentine.miniaturepotato.entity.Task;
import org.valentine.miniaturepotato.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Handles the HTTP POST request to create a new task.
     *
     * @param taskCreateDto the DTO containing task creation data
     * @param bindingResult the result of validation performed on the taskCreateDto
     * @return a ResponseEntity containing a success message and HTTP status CREATED if the task
     *         is created successfully, or an error message and HTTP status BAD_REQUEST if validation fails
     */
    @PostMapping
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskCreateDto taskCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }

        taskService.createTask(taskCreateDto.toEntity());

        return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
    }

    /**
     * Handles the HTTP PUT request to mark a task as completed.
     *
     * @param taskId the ID of the task to mark as completed
     * @return a ResponseEntity containing the completed task as a TaskResponseDto and HTTP status OK if the task is updated
     *         successfully, or an error message and HTTP status NOT_FOUND if the task ID is invalid
     */
    @PutMapping("/{taskId}/complete")
    public ResponseEntity<TaskResponseDto> completeTask(@PathVariable long taskId) {
        taskService.completeTask(taskId);
        TaskResponseDto completedTask = taskService.findByTaskId(taskId);
        return ResponseEntity.ok(completedTask);
    }

    /**
     * Handles the HTTP GET request to retrieve all incomplete tasks.
     *
     * @return a list of incomplete tasks
     */
    @GetMapping("/incomplete")
    public List<Task> getIncompleteTasks() {
        return taskService.findIncompleteTasks();
    }

    /**
     * Handles the HTTP GET request to retrieve a single task by ID.
     *
     * @param taskId the ID of the task to retrieve
     * @return a ResponseEntity containing the task as a TaskResponseDto and HTTP status OK if the task is found,
     *         or an error message and HTTP status NOT_FOUND if the task ID is invalid
     */
    @GetMapping("/{taskId}")
    public TaskResponseDto getTaskById(@PathVariable long taskId) {
        return taskService.findByTaskId(taskId);
    }
}

