package com.example.finalteammockdata.domain.kanban.controller;

import com.example.finalteammockdata.domain.kanban.service.KanbanService;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kanban")
public class KanbanController {

    private final KanbanService kanbanService;

    public KanbanController(KanbanService kanbanService) {
        this.kanbanService = kanbanService;
    }

    public ResponseEntity<BaseResponseDto<?>> getMainKanban(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(kanbanService.getMainKanban(userDetails.getUser().getId()));

    }
}
