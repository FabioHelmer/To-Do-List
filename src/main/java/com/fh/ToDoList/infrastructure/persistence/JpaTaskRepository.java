package com.fh.ToDoList.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTaskRepository extends JpaRepository<TaskEntity, UUID> {
}
