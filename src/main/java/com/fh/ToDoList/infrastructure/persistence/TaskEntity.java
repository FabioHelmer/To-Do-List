package com.fh.ToDoList.infrastructure.persistence;

import com.fh.ToDoList.domain.Task;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskEntity {

	@Id
	private UUID id;

	@Column(nullable = false, length = Task.MAX_TITLE_LENGTH)
	private String title;

	@Column(length = Task.MAX_DESCRIPTION_LENGTH)
	private String description;

	@Column(nullable = false)
	private boolean completed;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;

	protected TaskEntity() {
	}

	private TaskEntity(UUID id, String title, String description, boolean completed, LocalDateTime createdAt,
			LocalDateTime completedAt) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
		this.createdAt = createdAt;
		this.completedAt = completedAt;
	}

	public static TaskEntity from(Task task) {
		return new TaskEntity(
				task.getId(),
				task.getTitle(),
				task.getDescription(),
				task.isCompleted(),
				task.getCreatedAt(),
				task.getCompletedAt());
	}

	public Task toDomain() {
		return new Task(id, title, description, completed, createdAt, completedAt);
	}
}
