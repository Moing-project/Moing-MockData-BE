package com.example.finalteammockdata.domain.workspace.controller;

import com.example.finalteammockdata.domain.workspace.dto.WorkCreateDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkListResponseDto;
import com.example.finalteammockdata.domain.workspace.service.WorkService;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/work")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }


    @PostMapping("/create")
    public ResponseEntity<MessageResponseDto> createWorkspace(@RequestBody WorkCreateDto createDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(workService.createWorkspace(createDto, userId));
    }

    @GetMapping()
    public ResponseEntity<List<WorkListResponseDto>> getWorkspaces(){
        return ResponseEntity.ok(workService.getWorkspaces());
    }
}