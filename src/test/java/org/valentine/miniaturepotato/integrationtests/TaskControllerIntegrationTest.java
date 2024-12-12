package org.valentine.miniaturepotato.integrationtests;

import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    /**
     * Verifies that the HTTP POST request to create a new task returns a success message with HTTP status CREATED.
     *
     * @throws Exception if the test fails
     */
    @Test
    void createTask_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"New Task\"," +
                                "\"description\":\"Description\"," +
                                "\"priority\":\"HIGH\"," +
                                "\"dueDate\":\"2024-12-31T12:00:00\"}"))
                .andExpect(status().isCreated());
    }


    /**
     * Verifies that the HTTP GET request to retrieve all incomplete tasks returns a list
     * of tasks with HTTP status OK.
     *
     * @throws Exception if the test fails
     */
    @Test
    void findIncompleteTasks_ShouldReturnTasks() throws Exception {
        mockMvc.perform(get("/api/tasks/incomplete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(JSONArray.class)));
    }


    /**
     * Verifies that the HTTP GET request for a non-existent task ID returns a
     * HTTP status of NOT_FOUND.
     *
     * @throws Exception if the test fails
     */
    @Test
    void findByTaskId_NotFound_ShouldReturnNotFoundStatus() throws Exception {
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound());
    }
}