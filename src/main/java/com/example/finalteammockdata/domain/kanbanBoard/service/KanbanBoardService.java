package com.example.finalteammockdata.domain.kanbanBoard.service;

import com.example.finalteammockdata.domain.kanbanBoard.dto.KanbanBoardRequestDto;
import com.example.finalteammockdata.domain.kanbanBoard.entity.KanbanBoard;
import com.example.finalteammockdata.domain.kanbanBoard.repository.KanbanBoardRepository;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KanbanBoardService {
    private final KanbanBoardRepository kanbanBoardRepository;
    public KanbanBoardService(KanbanBoardRepository kanbanBoardRepository){
        this.kanbanBoardRepository = kanbanBoardRepository;
    }

    @Transactional
    public BaseResponseDto createKanban(KanbanBoardRequestDto requestDto, UserDetailsImpl userDetails){

        KanbanBoard newkanbanBoard = new KanbanBoard(requestDto, userDetails)
    }
}
