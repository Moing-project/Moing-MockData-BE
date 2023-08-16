package com.example.finalteammockdata.domain.workspace.dto;

import java.util.List;


public record WorkCreateDto(String title, String subject, Integer needMember, String date, String allowType, List<String> stacks, String introduce, String imageSrc) {
}
