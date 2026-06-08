package com.fh.ToDoList.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskTest {

	@Test
	void shouldCreatePendingTaskWithNormalizedFields() {
		Task task = Task.create("  Study tests  ", "  JUnit and AssertJ  ");

		assertThat(task.getId()).isNotNull();
		assertThat(task.getTitle()).isEqualTo("Study tests");
		assertThat(task.getDescription()).isEqualTo("JUnit and AssertJ");
		assertThat(task.isCompleted()).isFalse();
		assertThat(task.getCreatedAt()).isNotNull();
		assertThat(task.getCompletedAt()).isNull();
	}

	@Test
	void shouldRejectBlankTitle() {
		assertThatThrownBy(() -> Task.create(" ", "Any description"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("title is required");
	}

	@Test
	void shouldCompleteAndReopenTask() {
		Task task = Task.create("Pay invoice", null);

		task.complete();

		assertThat(task.isCompleted()).isTrue();
		assertThat(task.getCompletedAt()).isNotNull();

		task.reopen();

		assertThat(task.isCompleted()).isFalse();
		assertThat(task.getCompletedAt()).isNull();
	}

	@Test
	void shouldRequireCompletedAtWhenTaskStartsCompleted() {
		assertThatThrownBy(() -> new Task(UUID.randomUUID(), "Task", "", true, LocalDateTime.now(), null))
				.isInstanceOf(NullPointerException.class)
				.hasMessage("completedAt is required");
	}
}
