package com.example.finalteammockdata.domain.workspace.repository;

import com.example.finalteammockdata.domain.workspace.enums.WorkStackEnum;

import java.util.List;

public interface QWorkStackRepository {

    List<WorkStackEnum> findAllByWorkIdToStack(Long workId);
}
