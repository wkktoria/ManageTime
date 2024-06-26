package io.github.wkktoria.mantime.controller;

import io.github.wkktoria.mantime.model.Task;
import io.github.wkktoria.mantime.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {
    @Qualifier("testRepository")
    @Autowired
    TaskRepository repository;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void httpGet_returnsAllTheTasks() {
        // given
        var initialSize = repository.findAll().size();
        repository.save(new Task("foo", LocalDateTime.now()));
        repository.save(new Task("bar", LocalDateTime.now()));

        // when
        var result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);

        // then
        assertThat(result).hasSize(initialSize + 2);
    }

    @Test
    void httpGet_returnsGivenTask() {
        // given
        var description = "foo";
        var deadline = LocalDateTime.now();
        var task = repository.save(new Task(description, deadline));

        // when
        var result = restTemplate.getForObject("http://localhost:" + port + "/tasks/" + task.getId(), Task.class);

        // then
        assertThat(result.getDescription()).isEqualTo(description);
        assertThat(result.getDeadline()).isEqualTo(deadline);
    }
}