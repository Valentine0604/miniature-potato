package org.valentine.miniaturepotato.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.valentine.miniaturepotato.dto.TaskCreateDto;
import org.valentine.miniaturepotato.dto.TaskResponseDto;
import org.valentine.miniaturepotato.entity.Task;
import org.valentine.miniaturepotato.exception.TaskNotFoundException;
import org.valentine.miniaturepotato.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskCreateDto taskCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }

        TaskResponseDto createdTask  = TaskResponseDto.fromEntity(
                taskService.createTask(taskCreateDto.toEntity())
        );

        return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}/complete")
    public ResponseEntity<TaskResponseDto> completeTask(@PathVariable long taskId) {
        taskService.completeTask(taskId);

        TaskResponseDto completedTask = TaskResponseDto.fromEntity(
                taskService.findIncompleteTasks().stream()
                        .filter(task -> task.getId() == taskId)
                        .findFirst()
                        .orElseThrow(() -> new TaskNotFoundException(taskId))
        );

        return ResponseEntity.ok(completedTask);
    }

    @GetMapping("/incomplete")
    public List<Task> getIncompleteTasks() {
        return taskService.findIncompleteTasks();
    }
}

