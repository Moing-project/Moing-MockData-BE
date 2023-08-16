package com.example.finalteammockdata.domain.workspace.enums;

public enum WorkAllowMember {

    ALL_ALLOW("all_allow"),
    REQUIRED_ALLOW("required_allow"),
    NOT_ALLOW("not_allow");

    String allowType;

    WorkAllowMember(String allowType) {
        this.allowType = allowType;
    }

    public String getType(){
        return this.allowType;
    }

    public static WorkAllowMember get(String str){
        for (WorkAllowMember value : values()) {
            if(value.allowType.equalsIgnoreCase(str))
                return value;
        }
        return null;
    }
}
