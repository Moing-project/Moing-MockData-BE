package com.example.finalteammockdata.domain.comment.service;

import com.example.finalteammockdata.domain.auth.entity.AuthUser;
import com.example.finalteammockdata.domain.comment.dto.CommentRequestDto;
import com.example.finalteammockdata.domain.comment.entity.Comment;
import com.example.finalteammockdata.domain.comment.repository.CommentRepository;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.domain.workspace.service.WorkService;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final WorkService workService;
    public CommentService(CommentRepository commentRepository,WorkService workService){
        this.workService=workService;
        this.commentRepository = commentRepository;
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public BaseResponseDto createComment(Long workId, CommentRequestDto requestDto, UserDetailsImpl userDetails){
        Workspace workspace = workService.findByIdToWorkspace(workId);
        if(workspace == null)
            return BaseResponseDto.builder().msg("해당 Workspace를 찾을 수 없습니다").build();

        Comment comment = new Comment(requestDto, workspace, userDetails);
        commentRepository.save(comment);

        return BaseResponseDto.builder().msg("댓글 작성 성공").data(comment).build();
    }

    //<댓글 수정>
    @Transactional
    public MessageResponseDto updateComment(Long workId, Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails){
        Comment targetComment = findComment(workId, commentId);
        checkAuthority(targetComment, userDetails );
        targetComment.update(requestDto);

        return BaseResponseDto.builder().msg("댓글 수정 성공").data(targetComment).build();
    }

    //<댓글 삭제>
    public MessageResponseDto deleteComment(Long workId, Long commentId, UserDetailsImpl userDetails){
        Comment targetComment = findComment(workId, commentId);
        checkAuthority(targetComment, userDetails );
        commentRepository.delete(targetComment);

        return  BaseResponseDto.builder().msg("댓글을 삭제하였습니다").build();
    }

    //<댓글 찾기>
    public Comment findComment(Long workId, Long commentId){
        workService.findByIdToWorkspace(workId);
        return commentRepository.findByWorkIdAndCommentId(workId, commentId).orElseThrow(()->
                new NullPointerException("댓글이 존재하지 않습니다."));
    }

    //<댓글 수정 권한 확인>
    public void checkAuthority(Comment comment, UserDetailsImpl userDetails){
        if(!userDetails.getAuthorities().equals("ROLE_ADMIN")){
            if(!comment.getUserId().equals(userDetails.getUser().getId())){
                throw new AuthorizationServiceException("수정 권한이 없습니다.");
            }
        }
    }
}
