package com.example.finalteammockdata.domain.team.dto;

import com.example.finalteammockdata.domain.auth.dto.AuthWorkSoloResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public record TeamOneResponseDto(
        String ImageSrc,
        String allowType,
        LocalDateTime lastTime,
        Integer needMember,
        List<AuthWorkSoloResponseDto> contribution,
        String introduce,
        List<String> stacks) {
}
