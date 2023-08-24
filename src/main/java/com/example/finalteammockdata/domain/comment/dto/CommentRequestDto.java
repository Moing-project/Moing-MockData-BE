package com.example.finalteammockdata.domain.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotEmpty
    @Size(min=1, max = 255, message = "255내로 댓글을 입력해주세요.")
    private String comment;
}
