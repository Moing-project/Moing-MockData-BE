package com.example.finalteammockdata.domain.workspace.enums;

public enum WorkAllowEnum {

    ALL_ALLOW("all_allow"),
    REQUIRED_ALLOW("required_allow"),
    NOT_ALLOW("not_allow"),
    SECRET("secret");

    private final String allowType;

    WorkAllowEnum(String allowType) {
        this.allowType = allowType;
    }

    public String getType(){
        return this.allowType;
    }

    public static WorkAllowEnum get(String str){
        for (WorkAllowEnum value : values()) {
            if(value.allowType.equalsIgnoreCase(str))
                return value;
        }
        return null;
    }
}
