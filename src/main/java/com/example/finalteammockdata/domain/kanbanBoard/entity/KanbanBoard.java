package com.example.finalteammockdata.domain.kanbanBoard.entity;

import com.example.finalteammockdata.domain.kanbanBoard.dto.KanbanBoardRequestDto;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.global.maps.Timestamped;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class KanbanBoard extends Timestamped {
    @Id
    @GeneratedValue
    public Long kanbanBoardId;

    @Column(name="workspaceId")
    private Long workSpaceId;

    @Column(name="boardName")
    private String boardName;

    @Column(name="todo")
    private String todo;

    @Column(name="participants")
    private String participants;

    @Column(name="userId")
    private String writer;

    @Column
    private int position=0;



    public KanbanBoard(KanbanBoardRequestDto requestDto, Workspace workspace, UserDetailsImpl userDetails){
        this.workSpaceId= workspace.getId();
        this.boardName = requestDto.getBoardName();
        this.todo= requestDto.getTodo();
        this.participants= requestDto.getParticipants();
        this.writer= userDetails.getUsername();
    }

    public void update(KanbanBoardRequestDto requestDto){
        this.boardName=requestDto.getBoardName();
        this.todo=requestDto.getTodo();
        this.participants=requestDto.getParticipants();
        this.modifiedAt= LocalDateTime.now();
    }

}
