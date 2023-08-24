package com.example.finalteammockdata.domain.comment.controller;


import com.example.finalteammockdata.domain.comment.dto.CommentRequestDto;
import com.example.finalteammockdata.domain.comment.service.CommentService;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.global.jwt.JwtUtil;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work/{workId}/comment")
public class CommentController {
    private final CommentService commentService;

    private final JwtUtil jwtUtil;

    public CommentController(CommentService commentService,JwtUtil jwtUtil){
        this.commentService=commentService;
        this.jwtUtil=jwtUtil;
    }
    @PostMapping()
    public ResponseEntity<MessageResponseDto> createComment(@PathVariable Long workId,
                                                            @RequestBody @Valid CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(commentService.createComment(workId, requestDto, userDetails));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<MessageResponseDto> updateComment(@PathVariable Long workId,
                                           @PathVariable Long commentId,
                                           @RequestBody @Valid CommentRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return  ResponseEntity.ok(commentService.updateComment(workId, commentId, requestDto,userDetails));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponseDto> deleteComment(@PathVariable Long workId,
                                           @PathVariable Long commentId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(commentService.deleteComment(workId, commentId, userDetails));
    }
}
