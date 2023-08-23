package com.example.finalteammockdata.domain.kanbanBoard.controller;

import com.example.finalteammockdata.domain.kanbanBoard.dto.KanbanBoardRequestDto;
import com.example.finalteammockdata.domain.kanbanBoard.service.KanbanBoardService;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.domain.workspace.repository.WorkRepository;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.jwt.JwtUtil;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Getter
@RequestMapping("/api/kanbanboard")
public class KanbanBoardController {

    private final KanbanBoardService kanbanBoardService;
    private final WorkRepository workRepository;
    private final JwtUtil jwtUtil;

    public KanbanBoardController(KanbanBoardService kanbanBoardService, JwtUtil jwtUtil, WorkRepository workRepository ){
        this.kanbanBoardService = kanbanBoardService;
        this.jwtUtil = jwtUtil;
        this.workRepository = workRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponseDto> createKanban(@RequestBody @Valid KanbanBoardRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        ){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(kanbanBoardService.createKanban(requestDto, userDetails));
    }



}
