package org.valentine.miniaturepotato.web;

import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{taskId}/complete")
    public void completeTask(@PathVariable String taskId) {
        taskService.completeTask(taskId);
    }

    @GetMapping("/incomplete")
    public List<Task> getIncompleteTasks() {
        return taskService.findIncompleteTasks();
    }
}

