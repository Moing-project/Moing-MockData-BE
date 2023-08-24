package com.example.finalteammockdata.domain.workspace.repository;

import com.example.finalteammockdata.domain.workspace.dao.WorkMainDao;
import com.example.finalteammockdata.domain.workspace.entity.WorkTeam;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;

import java.util.List;

public interface QWorkRepository {
    List<Workspace> findAllOrderByAllowType();
    List<Workspace> findAllOrderByAllowTypeToSort();

    List<WorkMainDao> findAllByListWorkIdToMainDao(List<WorkTeam> workList);
}
