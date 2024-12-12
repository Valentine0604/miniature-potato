package org.valentine.miniaturepotato.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {super("Task not found");}
    public TaskNotFoundException(long taskId) {
        super("Task with id " + taskId + " not found");
    }
}
