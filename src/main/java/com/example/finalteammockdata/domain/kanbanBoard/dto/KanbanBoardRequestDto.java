package com.example.finalteammockdata.domain.kanbanBoard.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class KanbanBoardRequestDto {

    @NotBlank
    private String boardName;

    @NotBlank
    @Size(min = 1, max = 255, message = "1자 ~ 255자 내외로 작성해주세요.")
    private String todo;

    private String participants;


}
