package com.example.finalteammockdata.global.enums;

public enum AuthGlobalUserEnum {
    FREE(Authority.FREE),
    ENTERPRISE(Authority.ENTERPRISE),
    ADMIN(Authority.ADMIN);  // 관리자 권한

    private final String authority;

    AuthGlobalUserEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String FREE = "ROLE_FREE";
        public static final String ENTERPRISE = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}