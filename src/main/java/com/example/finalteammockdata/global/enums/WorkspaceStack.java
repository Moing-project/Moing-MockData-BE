package com.example.finalteammockdata.global.enums;

public enum WorkspaceStack {
    REACT("react");

    String stack;

    WorkspaceStack(String stack){
        this.stack = stack;
    }

    public String getStack() {
        return stack;
    }

    public static WorkspaceStack get(String str){
        for (WorkspaceStack value : values()) {
            if(value.stack.equalsIgnoreCase(str))
                return value;
        }
        return null;
    }

}
