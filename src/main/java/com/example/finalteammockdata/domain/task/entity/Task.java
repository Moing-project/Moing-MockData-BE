package com.example.finalteammockdata.domain.task.entity;

import com.example.finalteammockdata.domain.task.enums.TaskKanbanEnum;
import com.example.finalteammockdata.global.maps.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Task extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long workId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    // Kanban-board
    private TaskKanbanEnum kanbanType;

    // Calendar
    private LocalDateTime startTime;

    private LocalDateTime lastTime;
}
