package com.example.finalteammockdata.domain.workspace.controller;

import com.example.finalteammockdata.domain.workspace.dto.WorkCreateRequestDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkListResponseDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkRequestDto;
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
@RequestMapping("/api/work")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }


    @PostMapping("/create")
    public ResponseEntity<MessageResponseDto> createWorkspace(@RequestBody WorkCreateRequestDto createDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(workService.createWorkspace(createDto, userId));
    }

    @GetMapping()
    public ResponseEntity<List<WorkListResponseDto>> getWorkspaces(){
        return ResponseEntity.ok(workService.getWorkspaces());
    }

    @GetMapping("/{workId}")
    public ResponseEntity<WorkResponseDto> getWorkspace(@PathVariable Long workId){
        return ResponseEntity.ok(workService.getWorkspace(workId));
    }

//    수정
    @PatchMapping("/{workId}")
    public ResponseEntity<MessageResponseDto> updateWorkspace(@PathVariable Long workId,
                                                              @RequestBody WorkRequestDto workRequestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(workService.updateWorkspace(workId, workRequestDto,userDetails));
    }

    //삭제
    @DeleteMapping("/{workId}")
    public ResponseEntity<MessageResponseDto> deleteWorkspace(@PathVariable Long workId,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return  ResponseEntity.ok(workService.deleteWorkspace(workId, userDetails));
    }
//
//    //초대
//    @PostMapping("/invite")
//    public ResponseEntity<MessageResponseDto> inviteWorkspace(){
//        return null ;
//    }
//
//
//    //신청
//    @PostMapping("/invite/{workId}")
//    public ResponseEntity<MessageResponseDto> applyWorkspace(){
//        return  null;
//    }
//
//    //신청 조회
//    @GetMapping("/invite/{workId}")
//    public ResponseEntity<MessageResponseDto> getApplyWorkspaces(){
//        return  null;
//    }
//
//    //신청 승인
//    @PostMapping("/invite/{userId]")
//    public ResponseEntity<MessageResponseDto> admitWorkspace(){
//        return  null;
//    }


}
