package org.valentine.miniaturepotato.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.valentine.miniaturepotato.entity.Priority;
import org.valentine.miniaturepotato.entity.Task;
import org.valentine.miniaturepotato.service.EmailContentBuilder;
import org.valentine.miniaturepotato.service.EmailNotificationService;
import org.valentine.miniaturepotato.service.EmailSender;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class EmailNotificationServiceTest {

    @Mock
    private EmailSender emailSender;

    @Mock
    private EmailContentBuilder emailContentBuilder;

    @InjectMocks
    private EmailNotificationService emailNotificationService;

    private Task testTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testTask = new Task(
                1L,
                "Test Task",
                "This is a test task.",
                LocalDateTime.now(),
                LocalDateTime.of(2024, 12, 31, 12, 0),
                Priority.HIGH,
                false
        );
    }

    @Test
    void sendTaskCreationNotification_ShouldBuildAndSendEmail() {
        // Arrange
        String expectedContent = "Email content for task creation.";
        when(emailContentBuilder.buildTaskCreationContent(testTask)).thenReturn(expectedContent);

        // Act
        emailNotificationService.sendTaskCreationNotification(testTask);

        // Assert
        verify(emailContentBuilder, times(1)).buildTaskCreationContent(testTask);
        verify(emailSender, times(1)).sendEmail(
                eq("ewecia.s@gmail.com"), // Using the hardcoded email
                eq("New Task Created: " + testTask.getTitle()),
                eq(expectedContent)
        );
    }

    @Test
    void sendTaskReminderNotification_ShouldBuildAndSendEmail() {
        // Arrange
        String expectedContent = "Email content for task reminder.";
        when(emailContentBuilder.buildTaskReminderContent(testTask)).thenReturn(expectedContent);

        // Act
        emailNotificationService.sendTaskReminderNotification(testTask);

        // Assert
        verify(emailContentBuilder, times(1)).buildTaskReminderContent(testTask);
        verify(emailSender, times(1)).sendEmail(
                eq("ewecia.s@gmail.com"), // Using the hardcoded email
                eq("Task Reminder: " + testTask.getTitle()),
                eq(expectedContent)
        );
    }
}