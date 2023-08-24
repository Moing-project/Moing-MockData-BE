package com.example.finalteammockdata.domain.task.repository;

import com.example.finalteammockdata.domain.task.dao.TaskKanbanDao;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.finalteammockdata.domain.task.entity.QTask.task;

@Repository
public class QTaskRepositoryImpl implements QTaskRepository {

    private final JPAQueryFactory queryFactory;

    public QTaskRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<TaskKanbanDao> getListTaskKanban(List<Long> taskList) {
        List<TaskKanbanDao> result = queryFactory.select(task.id, task.title, task.description, task.kanbanType)
                .from(task)
                .where(task.id.in(taskList))
                .orderBy(task.id.asc())
                .fetch().stream()
                .map(e ->
                        new TaskKanbanDao(
                                e.get(task.id),
                                e.get(task.title),
                                e.get(task.description),
                                e.get(task.kanbanType)))
                .toList();
        return result;
    }
}
