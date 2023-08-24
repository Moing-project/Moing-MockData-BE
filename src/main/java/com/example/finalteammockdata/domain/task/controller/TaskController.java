package com.example.finalteammockdata.domain.task.controller;

import com.example.finalteammockdata.global.dto.MessageResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    public ResponseEntity<MessageResponseDto> createTask(){
        return null;
    }

    public ResponseEntity<MessageResponseDto> updateTask(){
        return null;
    }

    public ResponseEntity<MessageResponseDto> deleteTask(){
        return null;
    }
}
