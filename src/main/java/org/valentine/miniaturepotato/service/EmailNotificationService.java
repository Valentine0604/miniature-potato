package org.valentine.miniaturepotato.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.valentine.miniaturepotato.entity.Task;

@Service
@AllArgsConstructor
public class EmailNotificationService implements NotificationService {

    private final EmailSender emailSender;
    private final EmailContentBuilder emailContentBuilder;


    @Override
    public void sendTaskCreationNotification(Task task) {
        //todo: replace with actual email
        String recipientEmail = "ewecia.s@gmail.com";

        String content = emailContentBuilder.buildTaskCreationContent(task);

        emailSender.sendEmail(
                recipientEmail,
                "New Task Created: " + task.getTitle(),
                content
        );
    }

    @Override
    public void sendTaskReminderNotification(Task task) {
        //todo: replace with actual email
        String recipientEmail = "ewecia.s@gmail.com";

        String content = emailContentBuilder.buildTaskReminderContent(task);

        emailSender.sendEmail(
                recipientEmail,
                "Task Reminder: " + task.getTitle(),
                content
        );
    }
}
