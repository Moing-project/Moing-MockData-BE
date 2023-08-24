package com.example.finalteammockdata.domain.comment.entity;

import com.example.finalteammockdata.domain.comment.dto.CommentRequestDto;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.global.maps.Timestamped;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name= "comment")
public class Comment extends Timestamped {

    public Comment(){};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Long workspaceId;

    private Long userId;

    private String writer;

    private String userImage;

    private String comment;

    public Comment(CommentRequestDto requestDto,Workspace workspace, UserDetailsImpl userDetails){
        this.workspaceId=workspace.getId();
        this.userId= userDetails.getUser().getId();
        this.writer=userDetails.getUser().getNickname();
        this.userImage=userDetails.getUser().getProfileImage();
        this.comment= requestDto.getComment();
        this.createdAt= LocalDateTime.now();
    }

    public void update(CommentRequestDto requestDto){
        this.comment = requestDto.getComment();
    }

}
