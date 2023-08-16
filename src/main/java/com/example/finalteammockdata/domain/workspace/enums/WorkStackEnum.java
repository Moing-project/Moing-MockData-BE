package com.example.finalteammockdata.domain.workspace.enums;

public enum WorkStackEnum {
    REACT("React"),
    JAVASCRIPT("JavaScript"),
    TYPESCRIPT("TypeScript"),
    VUE("Vue"),
    ANGULAR("Angular"),
    SVELTE("Svelte"),
    PYTHON("Python"),
    JAVA("Java"),
    C_CPLUSPLUS("C/C++"),
    C_SHAP("C#"),
    SWIFT("Swift"),
    DART("Dart"),
    NODE_JS("Node.js"),
    GO("Go"),
    SPRING("Spring"),
    DJANGO("Django"),
    NEST_JS("NestJS"),
    EXPRESS("Express"),
    GRAPH_QL("GraphQL"),
    SQL("SQL"),
    MONGO_DB("MongoDB"),
    FIRE_BASE("FireBase"),
    I_OS("iOS"),
    ANDROID("Android"),
    REACT_NATIVE("React Native"),
    AWS("AWS"),
    KUBERNETES("Kubernetes"),
    DOCKER("Docker"),
    GIT("Git"),
    FIGMA("Figma"),
    ZEPLIN("Zeplin");

    private final String stack;

    WorkStackEnum(String stack){
        this.stack = stack;
    }

    public String getStack() {
        return stack;
    }

    public static WorkStackEnum get(String str){
        for (WorkStackEnum value : values()) {
            if(value.stack.equalsIgnoreCase(str))
                return value;
        }
        return null;
    }

}
