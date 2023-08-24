package com.example.finalteammockdata.domain.task.repository;

import com.example.finalteammockdata.domain.task.entity.TaskCollaborators;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCollaboratorsRepository extends JpaRepository<TaskCollaborators, Long>, QTaskCollaboratorsRepository{
}
