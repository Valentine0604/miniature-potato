package org.valentine.miniaturepotato.service;

import org.springframework.stereotype.Component;
import org.valentine.miniaturepotato.entity.Task;
import static org.valentine.miniaturepotato.constants.DateConstants.DATE_FORMATTER;

/**
 * Responsible for constructing email content.
 * Adheres to Single Responsibility Principle by separating email content creation.
 */
@Component
public class EmailContentBuilder {

    /**
     * Builds task creation notification email content.
     *
     * @param task The task for which notification is being sent
     * @return Formatted email content
     */
    public String buildTaskCreationContent(Task task) {
        return String.format(
                "New Task Created:\n\n" +
                        "Task Details:\n" +
                        "Title: %s\n" +
                        "Description: %s\n" +
                        "Priority: %s\n" +
                        "Due Date: %s\n" +
                        "Created At: %s\n\n" +
                        "Please review and manage your task.",
                task.getTitle(),
                task.getDescription() != null ? task.getDescription() : "No description",
                task.getPriority() != null ? task.getPriority() : "Not specified",
                task.getDueDate() != null ? task.getDueDate().format(DATE_FORMATTER) : "No due date",
                task.getCreatedAt() != null ? task.getCreatedAt().format(DATE_FORMATTER) : "Just now"
        );
    }

        /**
         * Builds task reminder email content.
         *
         * @param task The task for which reminder is being sent
         * @return Formatted email content
         */
        public String buildTaskReminderContent(Task task) {
            return String.format(
                    "Task Reminder:\n\n" +
                            "Task Details:\n" +
                            "Title: %s\n" +
                            "Description: %s\n" +
                            "Priority: %s\n" +
                            "Due Date: %s\n\n" +
                            "This task is approaching its due date. Please take necessary action.",
                    task.getTitle(),
                    task.getDescription() != null ? task.getDescription() : "No description",
                    task.getPriority() != null ? task.getPriority() : "Not specified",
                    task.getDueDate() != null ? task.getDueDate().format(DATE_FORMATTER) : "No due date"
            );
        }
}

