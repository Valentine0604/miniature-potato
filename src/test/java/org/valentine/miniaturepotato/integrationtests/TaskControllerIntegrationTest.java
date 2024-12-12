package org.valentine.miniaturepotato.integrationtests;

import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.valentine.miniaturepotato.repository.TaskRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void createTask_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"New Task\"," +
                                "\"description\":\"Description\"," +
                                "\"priority\":\"HIGH\"," +
                                "\"dueDate\":\"2024-12-31T12:00:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void completeTask_ShouldReturnNoContent() throws Exception {
        // First create a task
        String taskContent = "{" +
                "\"title\":\"Task to Complete\"," +
                "\"description\":\"Completable Task\"," +
                "\"priority\":\"MEDIUM\"," +
                "\"dueDate\":\"2024-11-30T12:00:00\"}";

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskContent))
                .andExpect(status().isCreated());

        // Then complete the task
        mockMvc.perform(put("/api/tasks/1/complete"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findIncompleteTasks_ShouldReturnTasks() throws Exception {
        mockMvc.perform(get("/api/tasks/incomplete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(JSONArray.class)));
    }

    @Test
    void findByTaskId_ShouldReturnTask() throws Exception {
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void findByTaskId_NotFound_ShouldReturnNotFoundStatus() throws Exception {
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound());
    }
}