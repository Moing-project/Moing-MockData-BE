package com.example.finalteammockdata.domain.workspace.repository;

import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.domain.workspace.enums.WorkAllowEnum;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.finalteammockdata.domain.workspace.entity.QWorkspace.workspace;

@Repository
public class QWorkRepositoryImpl implements QWorkRepository{

    private final JPAQueryFactory queryFactory;

    public QWorkRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Workspace> findAllOrderByAllowType() {
        return queryFactory.select(workspace).from(workspace).where(workspace.allowType.ne(WorkAllowEnum.SECRET)).fetch();
    }

    @Override
    public List<Workspace> findAllOrderByAllowTypeToSort() {
        return queryFactory.select(workspace).from(workspace).where(workspace.allowType.ne(WorkAllowEnum.SECRET)).orderBy(workspace.createdAt.desc()).fetch();
    }
}
