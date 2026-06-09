package com.fh.ToDoList.web;

import com.fh.ToDoList.application.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Operacoes para gerenciamento de tarefas.")
@AllArgsConstructor
public class TaskController {

	private final TaskService taskService;

	@PostMapping
	@Operation(summary = "Cria uma tarefa", responses = {
			@ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados invalidos")
	})
	public ResponseEntity<TaskResponse> create(@RequestBody TaskRequest request) {
		TaskResponse response = TaskResponse.from(taskService.create(request.title(), request.description()));
		return ResponseEntity.created(URI.create("/tasks/" + response.id())).body(response);
	}

	@GetMapping
	@Operation(summary = "Lista todas as tarefas")
	public List<TaskResponse> findAll() {
		return taskService.findAll().stream()
				.map(TaskResponse::from)
				.toList();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busca uma tarefa pelo id", responses = {
			@ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
			@ApiResponse(responseCode = "404", description = "Tarefa nao encontrada")
	})
	public TaskResponse findById(@Parameter(description = "Id da tarefa") @PathVariable UUID id) {
		return TaskResponse.from(taskService.findById(id));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualiza uma tarefa", responses = {
			@ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados invalidos"),
			@ApiResponse(responseCode = "404", description = "Tarefa nao encontrada")
	})
	public TaskResponse update(@Parameter(description = "Id da tarefa") @PathVariable UUID id,
			@RequestBody TaskRequest request) {
		return TaskResponse.from(taskService.update(id, request.title(), request.description()));
	}

	@PatchMapping("/{id}/complete")
	@Operation(summary = "Marca uma tarefa como concluida", responses = {
			@ApiResponse(responseCode = "200", description = "Tarefa concluida com sucesso"),
			@ApiResponse(responseCode = "404", description = "Tarefa nao encontrada")
	})
	public TaskResponse complete(@Parameter(description = "Id da tarefa") @PathVariable UUID id) {
		return TaskResponse.from(taskService.complete(id));
	}

	@PatchMapping("/{id}/reopen")
	@Operation(summary = "Reabre uma tarefa concluida", responses = {
			@ApiResponse(responseCode = "200", description = "Tarefa reaberta com sucesso"),
			@ApiResponse(responseCode = "404", description = "Tarefa nao encontrada")
	})
	public TaskResponse reopen(@Parameter(description = "Id da tarefa") @PathVariable UUID id) {
		return TaskResponse.from(taskService.reopen(id));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Remove uma tarefa", responses = {
			@ApiResponse(responseCode = "204", description = "Tarefa removida com sucesso"),
			@ApiResponse(responseCode = "404", description = "Tarefa nao encontrada")
	})
	public ResponseEntity<Void> delete(@Parameter(description = "Id da tarefa") @PathVariable UUID id) {
		taskService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
