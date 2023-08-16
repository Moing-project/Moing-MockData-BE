package com.example.finalteammockdata.domain.workspace.dto;

import java.util.List;

public record WorkListResponseDto (Long id, String imageSrc, String title, List<String> stacks, String introduce){
}
