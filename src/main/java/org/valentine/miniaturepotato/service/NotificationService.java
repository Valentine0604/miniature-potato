package org.valentine.miniaturepotato.service;

import org.valentine.miniaturepotato.entity.Task;

public interface NotificationService {
    void sendTaskCreationNotification(Task task);
    void sendTaskReminderNotification(Task task);
}

