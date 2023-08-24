package com.example.finalteammockdata.domain.workspace.repository;

import com.example.finalteammockdata.domain.workspace.entity.WorkStack;
import com.example.finalteammockdata.domain.workspace.enums.WorkStackEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkStackRepository extends JpaRepository<WorkStack, Long> ,QWorkStackRepository {

    Optional<WorkStack> findByWorkIdAndStack(Long workId, WorkStackEnum stack);
}
