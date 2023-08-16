package com.example.finalteammockdata.domain.workspace.repository;

import com.example.finalteammockdata.global.enums.WorkspaceStack;

import java.util.List;

public interface QWorkStackRepository {

    List<WorkspaceStack> findAllByWorkIdToStack(Long workId);
}
