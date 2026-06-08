package com.fh.ToDoList.application;

import com.fh.ToDoList.domain.Task;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	private final TaskRepository taskRepository;

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public Task create(String title, String description) {
		return taskRepository.save(Task.create(title, description));
	}

	public List<Task> findAll() {
		return taskRepository.findAll().stream()
				.sorted(Comparator.comparing(Task::getCreatedAt))
				.toList();
	}

	public Task findById(UUID id) {
		return taskRepository.findById(id)
				.orElseThrow(() -> new TaskNotFoundException(id));
	}

	public Task update(UUID id, String title, String description) {
		Task task = findById(id);
		task.rename(title);
		task.changeDescription(description);
		return taskRepository.save(task);
	}

	public Task complete(UUID id) {
		Task task = findById(id);
		task.complete();
		return taskRepository.save(task);
	}

	public Task reopen(UUID id) {
		Task task = findById(id);
		task.reopen();
		return taskRepository.save(task);
	}

	public void delete(UUID id) {
		if (!taskRepository.existsById(id)) {
			throw new TaskNotFoundException(id);
		}

		taskRepository.deleteById(id);
	}
}
