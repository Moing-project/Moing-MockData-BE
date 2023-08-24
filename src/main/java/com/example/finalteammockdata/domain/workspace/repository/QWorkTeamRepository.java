package com.example.finalteammockdata.domain.workspace.repository;

import java.util.List;

public interface QWorkTeamRepository {

    List<Long> findAllSelectUserIdByWorkId(Long workId);
}
