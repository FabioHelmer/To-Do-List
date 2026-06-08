package com.fh.ToDoList.infrastructure.persistence;

import com.fh.ToDoList.application.TaskRepository;
import com.fh.ToDoList.domain.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class SqlServerTaskRepository implements TaskRepository {

	private final JpaTaskRepository jpaTaskRepository;

	public SqlServerTaskRepository(JpaTaskRepository jpaTaskRepository) {
		this.jpaTaskRepository = jpaTaskRepository;
	}

	@Override
	public Task save(Task task) {
		return jpaTaskRepository.save(TaskEntity.from(task)).toDomain();
	}

	@Override
	public Optional<Task> findById(UUID id) {
		return jpaTaskRepository.findById(id).map(TaskEntity::toDomain);
	}

	@Override
	public List<Task> findAll() {
		return jpaTaskRepository.findAll().stream()
				.map(TaskEntity::toDomain)
				.toList();
	}

	@Override
	public void deleteById(UUID id) {
		jpaTaskRepository.deleteById(id);
	}

	@Override
	public boolean existsById(UUID id) {
		return jpaTaskRepository.existsById(id);
	}
}
