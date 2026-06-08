package com.fh.ToDoList.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fh.ToDoList.domain.Task;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskServiceTest {

	private FakeTaskRepository taskRepository;
	private TaskService taskService;

	@BeforeEach
	void setUp() {
		taskRepository = new FakeTaskRepository();
		taskService = new TaskService(taskRepository);
	}

	@Test
	void shouldCreateTask() {
		Task task = taskService.create("Clean code", "Write focused unit tests");

		assertThat(task.getTitle()).isEqualTo("Clean code");
		assertThat(task.getDescription()).isEqualTo("Write focused unit tests");
		assertThat(taskRepository.findById(task.getId())).contains(task);
	}

	@Test
	void shouldReturnTasksOrderedByCreationDate() {
		Task newest = taskRepository.save(new Task(UUID.randomUUID(), "Newest", "", false,
				LocalDateTime.parse("2026-06-08T10:00:00"), null));
		Task oldest = taskRepository.save(new Task(UUID.randomUUID(), "Oldest", "", false,
				LocalDateTime.parse("2026-06-08T08:00:00"), null));

		List<Task> tasks = taskService.findAll();

		assertThat(tasks).containsExactly(oldest, newest);
	}

	@Test
	void shouldUpdateTask() {
		Task task = taskService.create("Old title", "Old description");

		Task updated = taskService.update(task.getId(), "New title", "New description");

		assertThat(updated.getTitle()).isEqualTo("New title");
		assertThat(updated.getDescription()).isEqualTo("New description");
	}

	@Test
	void shouldCompleteAndReopenTask() {
		Task task = taskService.create("Buy coffee", "");

		Task completed = taskService.complete(task.getId());

		assertThat(completed.isCompleted()).isTrue();
		assertThat(completed.getCompletedAt()).isNotNull();

		Task reopened = taskService.reopen(task.getId());

		assertThat(reopened.isCompleted()).isFalse();
		assertThat(reopened.getCompletedAt()).isNull();
	}

	@Test
	void shouldThrowWhenTaskDoesNotExist() {
		UUID unknownId = UUID.randomUUID();

		assertThatThrownBy(() -> taskService.findById(unknownId))
				.isInstanceOf(TaskNotFoundException.class)
				.hasMessage("Task not found: " + unknownId);
	}

	@Test
	void shouldDeleteTask() {
		Task task = taskService.create("Delete me", "");

		taskService.delete(task.getId());

		assertThat(taskRepository.existsById(task.getId())).isFalse();
	}

	private static class FakeTaskRepository implements TaskRepository {

		private final Map<UUID, Task> tasks = new HashMap<>();

		@Override
		public Task save(Task task) {
			tasks.put(task.getId(), task);
			return task;
		}

		@Override
		public Optional<Task> findById(UUID id) {
			return Optional.ofNullable(tasks.get(id));
		}

		@Override
		public List<Task> findAll() {
			return new ArrayList<>(tasks.values());
		}

		@Override
		public void deleteById(UUID id) {
			tasks.remove(id);
		}

		@Override
		public boolean existsById(UUID id) {
			return tasks.containsKey(id);
		}
	}
}
