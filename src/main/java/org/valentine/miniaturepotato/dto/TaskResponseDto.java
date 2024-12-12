package org.valentine.miniaturepotato.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.valentine.miniaturepotato.entity.Priority;
import org.valentine.miniaturepotato.entity.Task;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDto {

    private long taskId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
    private Priority priority;
    private boolean isCompleted;

    /**
     * Maps a {@link Task} entity to a {@link TaskResponseDto}.
     *
     * @param task the task to be mapped
     * @return the mapped task response DTO
     */
    public static TaskResponseDto fromEntity(Task task){
        return TaskResponseDto.builder()
                .taskId(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .createdAt(task.getCreatedAt())
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .isCompleted(task.isCompleted())
                .build();
    }

}
