package com.example.finalteammockdata.domain.introduce.enums;

public enum IntroduceFieldEnum {
    GAME("Game"),
    PLANNING("Planning"),
    MOBILE("Mobile"),
    BLOCKCHAIN("Blockchain"),
    SERVICE("Service"),
    STUDY("Study"),
    WEB("Web"),
    COTE_ALGORITHMS("Cote_Algorithms"),
    PROJECT("Project"),
    AI_MACHINE_LEARNING("AI_MachineLearning"),
    UI_UX("UI_UX");
    private final String field;

    IntroduceFieldEnum(String field){
        this.field=field;
    }

    public String getField(){
        return field;
    }

    public static IntroduceFieldEnum get(String str){
        for (IntroduceFieldEnum value : values()) {
            if(value.field.equalsIgnoreCase(str))
                return value;
        }
        return null;
    }

}
