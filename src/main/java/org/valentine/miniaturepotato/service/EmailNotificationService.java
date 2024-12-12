package org.valentine.miniaturepotato.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.valentine.miniaturepotato.entity.Task;

@Service
@AllArgsConstructor
public class EmailNotificationService implements NotificationService {

    private final EmailSender emailSender;
    private final EmailContentBuilder emailContentBuilder;


    /**
     * Sends a notification email for task creation.
     *
     * @param task The task for which the creation notification is being sent.
     *             The email will contain the task details such as title, description, priority, and due date.
     */
    @Override
    public void sendTaskCreationNotification(Task task) {
        String recipientEmail = "ewecia.s@gmail.com";

        String content = emailContentBuilder.buildTaskCreationContent(task);

        emailSender.sendEmail(
                recipientEmail,
                "New Task Created: " + task.getTitle(),
                content
        );
    }

    /**
     * Sends a reminder email for a task.
     *
     * @param task The task for which the reminder notification is being sent.
     *             The email will contain the task details such as title, description, priority, and due date.
     */
    @Override
    public void sendTaskReminderNotification(Task task) {
        String recipientEmail = "ewecia.s@gmail.com";

        String content = emailContentBuilder.buildTaskReminderContent(task);

        emailSender.sendEmail(
                recipientEmail,
                "Task Reminder: " + task.getTitle(),
                content
        );
    }
}
