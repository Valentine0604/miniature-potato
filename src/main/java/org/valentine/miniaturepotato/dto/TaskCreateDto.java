package org.valentine.miniaturepotato.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.valentine.miniaturepotato.entity.Priority;
import org.valentine.miniaturepotato.entity.Task;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskCreateDto {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot be longer than 1000 characters")
    private String description;

    @FutureOrPresent(message = "Date must be in the future or present")
    private LocalDateTime dueDate;

    private Priority priority;

    /**
     * Converts this TaskCreateDto to a Task entity.
     *
     * @return a new Task entity with fields populated from this DTO.
     */
    public Task toEntity() {
        return Task.builder()
                .title(this.title)
                .description(this.description)
                .dueDate(this.dueDate)
                .priority(this.priority)
                .completed(false)
                .build();
    }


}
