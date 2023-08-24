package com.example.finalteammockdata.domain.task.dao;

import com.example.finalteammockdata.domain.task.enums.TaskKanbanEnum;
import lombok.Getter;

@Getter
public class TaskKanbanDao {

    private final Long id;

    private final String title;

    private final String description;

    private final TaskKanbanEnum status;

    public TaskKanbanDao(Long id, String title, String description, TaskKanbanEnum status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }
}
