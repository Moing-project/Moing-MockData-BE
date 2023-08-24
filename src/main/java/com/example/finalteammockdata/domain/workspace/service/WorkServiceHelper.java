package com.example.finalteammockdata.domain.workspace.service;

import com.example.finalteammockdata.global.enums.AuthGlobalUserEnum;
import org.springframework.stereotype.Service;

@Service
public class WorkServiceHelper {
    public Integer createMaxMemberToUserRating(AuthGlobalUserEnum userRating){
        switch (userRating){
            case FREE -> {
                return 10;
            }
            case ENTERPRISE -> {
                return 100;
            }
            case ADMIN -> {
                return 0;
            }
            default -> {
                return 1;
            }
        }
    }
}
