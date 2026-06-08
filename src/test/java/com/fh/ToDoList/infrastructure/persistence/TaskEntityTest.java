package com.fh.ToDoList.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.fh.ToDoList.domain.Task;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskEntityTest {

	@Test
	void shouldMapDomainTaskToEntityAndBack() {
		UUID id = UUID.randomUUID();
		LocalDateTime createdAt = LocalDateTime.parse("2026-06-08T09:00:00");
		LocalDateTime completedAt = LocalDateTime.parse("2026-06-08T10:00:00");
		Task task = new Task(id, "Configure SQL Server", "Replace in-memory repository", true, createdAt,
				completedAt);

		Task mappedTask = TaskEntity.from(task).toDomain();

		assertThat(mappedTask.getId()).isEqualTo(id);
		assertThat(mappedTask.getTitle()).isEqualTo("Configure SQL Server");
		assertThat(mappedTask.getDescription()).isEqualTo("Replace in-memory repository");
		assertThat(mappedTask.isCompleted()).isTrue();
		assertThat(mappedTask.getCreatedAt()).isEqualTo(createdAt);
		assertThat(mappedTask.getCompletedAt()).isEqualTo(completedAt);
	}
}
