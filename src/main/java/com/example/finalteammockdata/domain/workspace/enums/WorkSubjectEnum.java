package com.example.finalteammockdata.domain.workspace.enums;

public enum WorkSubjectEnum {
    GAME("게임"),
    PLANNING("기획"),
    MOBILE("모바일"),
    BLOCK_CHAIN("블록체인"),
    SERVICE("서비스"),
    STUDY("스터디"),
    WEB("웹"),
    ALGORITHM("코테/알고리즘"),
    PROJECT("프로젝트"),
    MACHINE_LEARNING("AI/머신러닝"),
    UI_UX("UI/UX");

    private final String value;

    WorkSubjectEnum(String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }

    public static WorkSubjectEnum get(String keyOrValue){
        for (WorkSubjectEnum value : values()) {
            if(value.name().equalsIgnoreCase(keyOrValue)|| value.value().equals(keyOrValue))
                return value;
        }
        return null;
    }
}
