package org.valentine.miniaturepotato.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(long taskId) {
        super("Task with id " + taskId + " not found");
    }
}
