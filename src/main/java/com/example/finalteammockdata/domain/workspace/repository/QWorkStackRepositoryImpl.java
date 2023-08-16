package com.example.finalteammockdata.domain.workspace.repository;

import com.example.finalteammockdata.global.enums.WorkspaceStack;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.finalteammockdata.domain.workspace.entity.QWorkStack.workStack;

@Repository
public class QWorkStackRepositoryImpl implements QWorkStackRepository{

    private final JPAQueryFactory queryFactory;

    public QWorkStackRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<WorkspaceStack> findAllByWorkIdToStack(Long workId){
        return queryFactory.select(workStack.stacks).from(workStack).where(workStack.workId.eq(workId)).fetchAll().fetch();
    }
}
