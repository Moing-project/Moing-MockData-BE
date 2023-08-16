package com.example.finalteammockdata.domain.workspace.entity;

import com.example.finalteammockdata.global.enums.WorkspaceStack;
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
    WorkspaceStack stacks;

    public WorkStack() {
    }

    public WorkStack(Long workId, WorkspaceStack stacks) {
        this.workId = workId;
        this.stacks = stacks;
    }
}
