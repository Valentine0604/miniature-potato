package org.valentine.miniaturepotato.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String taskId) {
        super("Nie znaleziono zadania o ID: " + taskId);
    }
}
