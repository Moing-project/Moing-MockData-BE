package com.example.finalteammockdata.domain.kanbanBoard.repository;

import com.example.finalteammockdata.domain.kanbanBoard.entity.KanbanBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KanbanBoardRepository extends JpaRepository<KanbanBoard, Long> {
    Optional<KanbanBoard> findByKanbanBoardId(Long KanbanBoardId);
}
