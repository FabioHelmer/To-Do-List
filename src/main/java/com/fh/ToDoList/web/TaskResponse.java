package com.fh.ToDoList.web;

import com.fh.ToDoList.domain.Task;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponse(
		UUID id,
		String title,
		String description,
		boolean completed,
		LocalDateTime createdAt,
		LocalDateTime completedAt) {

	public static TaskResponse from(Task task) {
		return new TaskResponse(
				task.getId(),
				task.getTitle(),
				task.getDescription(),
				task.isCompleted(),
				task.getCreatedAt(),
				task.getCompletedAt());
	}
}
