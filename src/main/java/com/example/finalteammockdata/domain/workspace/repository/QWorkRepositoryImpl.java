package com.example.finalteammockdata.domain.workspace.repository;

import com.example.finalteammockdata.domain.workspace.dao.WorkMainDao;
import com.example.finalteammockdata.domain.workspace.entity.WorkTeam;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.domain.workspace.enums.WorkAllowEnum;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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

    @Override
    public List<WorkMainDao> findAllByListWorkIdToMainDao(List<WorkTeam> workList) {
        List<Tuple> resultLists = queryFactory.select(workspace.id, workspace.imageSrc, workspace.title)
                .from(workspace).where(workspace.id.in(workList.stream().map(WorkTeam::getWorkId).toList()))
                .fetch();
        for (Tuple result : resultLists) {
            WorkTeam workTeam = workList.stream().filter(e-> Objects.equals(e.getWorkId(), result.get(workspace.id))).findFirst().get();
        }
        return null;
    }
}
