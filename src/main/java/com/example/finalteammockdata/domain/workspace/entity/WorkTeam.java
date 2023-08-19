package com.example.finalteammockdata.domain.workspace.entity;

import com.example.finalteammockdata.domain.workspace.enums.WorkPermissionEnum;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class WorkTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long workId;

    Long userId;

    @Enumerated(value = EnumType.STRING)
    WorkPermissionEnum userPermission;

    public WorkTeam() {
    }

    public WorkTeam(Long workId, Long userId) {
        this.workId = workId;
        this.userId = userId;
    }
}
