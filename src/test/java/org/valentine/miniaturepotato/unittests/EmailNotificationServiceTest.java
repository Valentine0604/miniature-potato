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

    /**
     * Sets up the test class by initializing the test task and creating mocks
     * for the email sender and email content builder.
     */
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

    /**
     * Verifies that the task creation notification email is built and sent
     * successfully.
     * <p>
     * This test verifies that the email content is built using the
     * {@link EmailContentBuilder} and that the email is sent using the
     * {@link EmailSender}.
     */
    @Test
    void sendTaskCreationNotification_ShouldBuildAndSendEmail() {
        String expectedContent = "Email content for task creation.";
        when(emailContentBuilder.buildTaskCreationContent(testTask)).thenReturn(expectedContent);

        emailNotificationService.sendTaskCreationNotification(testTask);

        verify(emailContentBuilder, times(1)).buildTaskCreationContent(testTask);
        verify(emailSender, times(1)).sendEmail(
                eq("ewecia.s@gmail.com"),
                eq("New Task Created: " + testTask.getTitle()),
                eq(expectedContent)
        );
    }

    /**
     * Verifies that the task reminder notification email is built and sent
     * successfully.
     * <p>
     * This test checks if the email content is constructed using the
     * {@link EmailContentBuilder} and ensures the email is dispatched using the
     * {@link EmailSender}. It confirms that the correct email content and
     * subject are used when sending the reminder notification.
     */
    @Test
    void sendTaskReminderNotification_ShouldBuildAndSendEmail() {
        String expectedContent = "Email content for task reminder.";
        when(emailContentBuilder.buildTaskReminderContent(testTask)).thenReturn(expectedContent);

        emailNotificationService.sendTaskReminderNotification(testTask);

        verify(emailContentBuilder, times(1)).buildTaskReminderContent(testTask);
        verify(emailSender, times(1)).sendEmail(
                eq("ewecia.s@gmail.com"), // Using the hardcoded email
                eq("Task Reminder: " + testTask.getTitle()),
                eq(expectedContent)
        );
    }
}