package com.example.finalteammockdata.domain.workspace.dto;

import java.util.List;


public record WorkCreateRequestDto(String title,
                                   String subject,
                                   String date,
                                   String allowType,
                                   List<String> stacks,
                                   String introduce,
                                   String imageSrc) {
}