package com.example.finalteammockdata.domain.team.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TeamMainResponseDto (
        String imageSrc,
        String name,
        List<String> stacks,
        String introduce,
        LocalDateTime endTime
){

}
