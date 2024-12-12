package org.valentine.miniaturepotato.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.valentine.miniaturepotato.exception.TaskAlreadyCompleted;
import org.valentine.miniaturepotato.exception.TaskNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the {@link TaskAlreadyCompleted} exception thrown when a client attempts to complete a task that is already
     * marked as completed.
     *
     * @param ex the exception thrown
     * @return a ResponseEntity containing the error message and a HTTP status code of BAD_REQUEST
     */
    @ExceptionHandler(TaskAlreadyCompleted.class)
    public ResponseEntity<String> handleTaskAlreadyCompleted(TaskAlreadyCompleted ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles the {@link TaskNotFoundException} exception thrown when a client attempts to access a task that doesn't exist.
     *
     * @param ex the exception thrown
     * @return a ResponseEntity containing the error message and a HTTP status code of NOT_FOUND
     */
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
