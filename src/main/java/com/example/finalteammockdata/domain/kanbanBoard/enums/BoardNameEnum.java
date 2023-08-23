package com.example.finalteammockdata.domain.kanbanBoard.enums;

public enum BoardNameEnum {
    BEGIN("Begin"),
    INPROGRESS("InProgress"),
    COMPLETION("Completion"),

    SUSPENSION("Suspension");

    private final String boardName;

    BoardNameEnum(String boardName){
        this.boardName = boardName;
    }
    public static BoardNameEnum get(String str){
        for(BoardNameEnum value : values()){
            if(value.boardName.equalsIgnoreCase(str))
                return value;
        }
        return null;
    }


}
