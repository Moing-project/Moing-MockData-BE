package com.example.finalteammockdata.domain.kanban.service;

import com.example.finalteammockdata.global.dto.BaseResponseDto;
import org.springframework.stereotype.Service;

@Service
public class KanbanService {
    public BaseResponseDto<?> getMainKanban(Long id) {



        return BaseResponseDto.builder(id).build();
    }
}
