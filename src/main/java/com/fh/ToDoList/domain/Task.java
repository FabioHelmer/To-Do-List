package com.fh.ToDoList.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Task {

	public static final int MAX_TITLE_LENGTH = 150;
	public static final int MAX_DESCRIPTION_LENGTH = 1000;

	private final UUID id;
	private String title;
	private String description;
	private boolean completed;
	private final LocalDateTime createdAt;
	private LocalDateTime completedAt;

	public Task(UUID id, String title, String description, boolean completed, LocalDateTime createdAt,
			LocalDateTime completedAt) {
		this.id = Objects.requireNonNull(id, "id is required");
		this.title = validateTitle(title);
		this.description = normalizeDescription(description);
		this.completed = completed;
		this.createdAt = Objects.requireNonNull(createdAt, "createdAt is required");
		this.completedAt = completed ? Objects.requireNonNull(completedAt, "completedAt is required") : null;
	}

	public static Task create(String title, String description) {
		return new Task(UUID.randomUUID(), title, description, false, LocalDateTime.now(), null);
	}

	public void rename(String title) {
		this.title = validateTitle(title);
	}

	public void changeDescription(String description) {
		this.description = normalizeDescription(description);
	}

	public void complete() {
		if (completed) {
			return;
		}

		this.completed = true;
		this.completedAt = LocalDateTime.now();
	}

	public void reopen() {
		this.completed = false;
		this.completedAt = null;
	}

	public UUID getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getCompletedAt() {
		return completedAt;
	}

	private static String validateTitle(String title) {
		if (title == null || title.isBlank()) {
			throw new IllegalArgumentException("title is required");
		}

		String normalizedTitle = title.trim();
		if (normalizedTitle.length() > MAX_TITLE_LENGTH) {
			throw new IllegalArgumentException("title must have at most " + MAX_TITLE_LENGTH + " characters");
		}

		return normalizedTitle;
	}

	private static String normalizeDescription(String description) {
		if (description == null) {
			return "";
		}

		String normalizedDescription = description.trim();
		if (normalizedDescription.length() > MAX_DESCRIPTION_LENGTH) {
			throw new IllegalArgumentException(
					"description must have at most " + MAX_DESCRIPTION_LENGTH + " characters");
		}

		return normalizedDescription;
	}
}
