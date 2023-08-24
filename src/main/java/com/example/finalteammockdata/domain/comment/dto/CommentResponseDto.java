package com.example.finalteammockdata.domain.comment.dto;

import com.example.finalteammockdata.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private String userImage;
    private String nickname;
    private String comment;
    private LocalDateTime createdAt;


    public CommentResponseDto(Comment comment){
        this.userImage=comment.getUserImage();
        this.nickname=comment.getWriter();
        this.comment=comment.getComment();
        this.createdAt=comment.getCreatedAt();
    }
}
