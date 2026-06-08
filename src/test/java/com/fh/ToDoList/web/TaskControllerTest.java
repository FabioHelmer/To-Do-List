package com.fh.ToDoList.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fh.ToDoList.application.TaskNotFoundException;
import com.fh.ToDoList.application.TaskService;
import com.fh.ToDoList.domain.Task;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private TaskService taskService;

	@Test
	void shouldCreateTask() throws Exception {
		Task task = task("Build API");
		when(taskService.create("Build API", "Create endpoints")).thenReturn(task);

		mockMvc.perform(post("/tasks")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new TaskRequest("Build API", "Create endpoints"))))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", "/tasks/" + task.getId()))
				.andExpect(jsonPath("$.id").value(task.getId().toString()))
				.andExpect(jsonPath("$.title").value("Build API"))
				.andExpect(jsonPath("$.completed").value(false));
	}

	@Test
	void shouldFindAllTasks() throws Exception {
		when(taskService.findAll()).thenReturn(List.of(task("First"), task("Second")));

		mockMvc.perform(get("/tasks"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].title").value("First"))
				.andExpect(jsonPath("$[1].title").value("Second"));
	}

	@Test
	void shouldCompleteTask() throws Exception {
		Task task = task("Finish challenge");
		task.complete();
		when(taskService.complete(task.getId())).thenReturn(task);

		mockMvc.perform(patch("/tasks/{id}/complete", task.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.completed").value(true))
				.andExpect(jsonPath("$.completedAt").isNotEmpty());
	}

	@Test
	void shouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
		UUID unknownId = UUID.randomUUID();
		when(taskService.findById(unknownId)).thenThrow(new TaskNotFoundException(unknownId));

		mockMvc.perform(get("/tasks/{id}", unknownId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Task not found: " + unknownId));
	}

	@Test
	void shouldReturnBadRequestWhenBusinessValidationFails() throws Exception {
		when(taskService.create(eq(""), any())).thenThrow(new IllegalArgumentException("title is required"));

		mockMvc.perform(post("/tasks")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new TaskRequest("", ""))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("title is required"));
	}

	@Test
	void shouldDeleteTask() throws Exception {
		UUID taskId = UUID.randomUUID();

		mockMvc.perform(delete("/tasks/{id}", taskId))
				.andExpect(status().isNoContent());

		verify(taskService).delete(taskId);
	}

	@Test
	void shouldReturnNotFoundWhenDeletingUnknownTask() throws Exception {
		UUID unknownId = UUID.randomUUID();
		doThrow(new TaskNotFoundException(unknownId)).when(taskService).delete(unknownId);

		mockMvc.perform(delete("/tasks/{id}", unknownId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", startsWith("Task not found:")));
	}

	private Task task(String title) {
		return new Task(UUID.randomUUID(), title, "", false, LocalDateTime.parse("2026-06-08T09:00:00"), null);
	}
}
