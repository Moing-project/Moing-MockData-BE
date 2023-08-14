package com.example.finalteammockdata.domain.image.controller;


import com.example.finalteammockdata.domain.image.service.ImageService;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/image")
    public ResponseEntity<BaseResponseDto<List<String>>> uploadImage(@RequestPart("imageFile") List<MultipartFile> image){
        return ResponseEntity.ok(imageService.uploadImage(image));
    }

    @DeleteMapping("/image")
    public ResponseEntity<BaseResponseDto> deleteImage(List<String> image){
        return ResponseEntity.ok(imageService.deleteImage(image));
    }
}
