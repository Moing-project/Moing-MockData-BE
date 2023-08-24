package com.example.finalteammockdata.domain.task.repository;

import com.example.finalteammockdata.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {


}
