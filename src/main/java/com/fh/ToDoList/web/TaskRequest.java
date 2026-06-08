package com.fh.ToDoList.web;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criar ou atualizar uma tarefa.")
public record TaskRequest(
		@Schema(description = "Titulo da tarefa.", example = "Estudar testes unitarios", maxLength = 150)
		String title,

		@Schema(description = "Descricao da tarefa.", example = "Criar testes para dominio, aplicacao e controller.", maxLength = 1000)
		String description) {
}
