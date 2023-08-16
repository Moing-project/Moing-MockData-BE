package com.example.finalteammockdata.domain.workspace.entity;

import com.example.finalteammockdata.domain.workspace.enums.WorkStackEnum;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class WorkStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long workId;

    @Enumerated(value = EnumType.STRING)
    WorkStackEnum stacks;

    public WorkStack() {
    }

    public WorkStack(Long workId, WorkStackEnum stacks) {
        this.workId = workId;
        this.stacks = stacks;
    }
}
