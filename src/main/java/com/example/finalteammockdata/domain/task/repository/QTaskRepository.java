package com.example.finalteammockdata.domain.task.repository;

import com.example.finalteammockdata.domain.task.dao.TaskKanbanDao;

import java.util.List;

public interface QTaskRepository {

    List<TaskKanbanDao> getListTaskKanban(List<Long> taskList);
}
