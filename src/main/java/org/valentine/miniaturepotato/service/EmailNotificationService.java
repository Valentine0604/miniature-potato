package org.valentine.miniaturepotato.service;

import org.springframework.stereotype.Service;
import org.valentine.miniaturepotato.entity.Task;

@Service
public class EmailNotificationService implements NotificationService {
    @Override
    public void sendTaskCreationNotification(Task task) {
        System.out.println("Wysłano powiadomienie e-mail o utworzeniu zadania: " + task.getTitle());
    }

    @Override
    public void sendTaskReminderNotification(Task task) {
        System.out.println("Wysłano przypomnienie o zadaniu: " + task.getTitle());
    }
}
