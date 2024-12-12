package org.valentine.miniaturepotato.exception;

public class TaskAlreadyCompleted extends RuntimeException {
    public TaskAlreadyCompleted(long taskId) {
        super("Task with id " + taskId + " has already been completed");
    }
}
