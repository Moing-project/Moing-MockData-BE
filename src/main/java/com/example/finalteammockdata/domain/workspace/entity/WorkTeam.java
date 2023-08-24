package com.example.finalteammockdata.domain.workspace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class WorkTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long workId;

    Long userId;

    public WorkTeam() {
    }

    public WorkTeam(Long workId, Long userId) {
        this.workId = workId;
        this.userId = userId;
    }
}
