package com.fh.ToDoList.web;

import com.fh.ToDoList.application.TaskService;
import java.net.URI;
import java.util.List;
import java.util.UUID;
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
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping
	public ResponseEntity<TaskResponse> create(@RequestBody TaskRequest request) {
		TaskResponse response = TaskResponse.from(taskService.create(request.title(), request.description()));
		return ResponseEntity.created(URI.create("/tasks/" + response.id())).body(response);
	}

	@GetMapping
	public List<TaskResponse> findAll() {
		return taskService.findAll().stream()
				.map(TaskResponse::from)
				.toList();
	}

	@GetMapping("/{id}")
	public TaskResponse findById(@PathVariable UUID id) {
		return TaskResponse.from(taskService.findById(id));
	}

	@PutMapping("/{id}")
	public TaskResponse update(@PathVariable UUID id, @RequestBody TaskRequest request) {
		return TaskResponse.from(taskService.update(id, request.title(), request.description()));
	}

	@PatchMapping("/{id}/complete")
	public TaskResponse complete(@PathVariable UUID id) {
		return TaskResponse.from(taskService.complete(id));
	}

	@PatchMapping("/{id}/reopen")
	public TaskResponse reopen(@PathVariable UUID id) {
		return TaskResponse.from(taskService.reopen(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		taskService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
