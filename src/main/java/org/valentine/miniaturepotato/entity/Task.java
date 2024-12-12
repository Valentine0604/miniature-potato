package org.valentine.miniaturepotato.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.valentine.miniaturepotato.exception.TaskAlreadyCompleted;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotNull
    private String title;

    @NotNull
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "is_completed")
    private boolean completed;

    /**
     * Automatically sets createdAt to the current time when the task is being created.
     */
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Marks the task as completed.
     *
     * @throws TaskAlreadyCompleted if the task is already marked as completed
     */
    public void complete() {
        if (this.completed) throw new TaskAlreadyCompleted(this.id);
        this.completed = true;
    }
}