package com.fh.ToDoList.web;

import com.fh.ToDoList.domain.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Representacao de uma tarefa.")
public record TaskResponse(
		@Schema(description = "Identificador unico da tarefa.", example = "2f1f2c6e-6dd0-4f54-b0fc-cf660f44e5b8")
		UUID id,

		@Schema(description = "Titulo da tarefa.", example = "Estudar testes unitarios")
		String title,

		@Schema(description = "Descricao da tarefa.", example = "Criar testes para dominio, aplicacao e controller.")
		String description,

		@Schema(description = "Indica se a tarefa foi concluida.", example = "false")
		boolean completed,

		@Schema(description = "Data de criacao da tarefa.", example = "2026-06-08T09:00:00")
		LocalDateTime createdAt,

		@Schema(description = "Data de conclusao da tarefa.", example = "2026-06-08T10:00:00")
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
