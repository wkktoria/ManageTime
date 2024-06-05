package io.github.wkktoria.managetime.controller;

import io.github.wkktoria.managetime.model.Task;
import io.github.wkktoria.managetime.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository repository;

    @Test
    void httpFet_returnsGivenTask() throws Exception {
        // given
        var id = repository.save(new Task("foo", LocalDateTime.now())).getId();

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/" + id)).andExpect(MockMvcResultMatchers.status().isOk());
    }
}