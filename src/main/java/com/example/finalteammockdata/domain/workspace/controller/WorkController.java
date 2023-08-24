package com.example.finalteammockdata.domain.workspace.controller;

import com.example.finalteammockdata.domain.workspace.dto.WorkCreateRequestDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkListResponseDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkResponseDto;
import com.example.finalteammockdata.domain.workspace.service.WorkService;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
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
    public ResponseEntity<MessageResponseDto> createWorkspace(@RequestBody WorkCreateRequestDto createDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(workService.createWorkspace(createDto, userId, userDetails.getUser().getUserRole()));
    }

    @GetMapping("/main")
    public ResponseEntity<List<WorkListResponseDto>> getWorkspaces(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(workService.getWorkspaces(userDetails.getUser().getId()));
    }

    @GetMapping("/{workId}")
    public ResponseEntity<WorkResponseDto> getWorkspace(@PathVariable Long workId){
        return ResponseEntity.ok(workService.getWorkspace(workId));
    }
}
