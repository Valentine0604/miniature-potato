package org.valentine.miniaturepotato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.valentine.miniaturepotato.entity.Priority;
import org.valentine.miniaturepotato.entity.Task;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {

    private long taskId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
    private Priority priority;
    private boolean isCompleted;

    public static TaskResponseDto fromEntity(Task task){
        TaskResponseDto dto = new TaskResponseDto();
        dto.setTaskId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setDueDate(task.getDueDate());
        dto.setPriority(task.getPriority());
        dto.setCompleted(task.isCompleted());

        return dto;
    }

}
