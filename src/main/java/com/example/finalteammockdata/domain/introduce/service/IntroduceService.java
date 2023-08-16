package com.example.finalteammockdata.domain.introduce.service;

import com.example.finalteammockdata.domain.introduce.dto.*;
import com.example.finalteammockdata.domain.introduce.entity.Introduce;
import com.example.finalteammockdata.domain.introduce.exception.IntroduceNotFoundException;
import com.example.finalteammockdata.domain.introduce.repository.IntroduceRepository;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.global.enums.AuthGlobalUserEnum;
import com.example.finalteammockdata.global.image.ImageFolderEnum;
import com.example.finalteammockdata.global.image.S3ImageUploader;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "IntroduceService")
public class IntroduceService {

    private final IntroduceRepository introduceRepository;
    private final S3ImageUploader s3ImageUploader;

    public IntroduceService(IntroduceRepository introduceRepository, S3ImageUploader s3ImageUploader) {
        this.introduceRepository = introduceRepository;
        this.s3ImageUploader = s3ImageUploader;
    }

    //<분야별 조회>
    @Transactional
    public BaseResponseDto<Page<Introduce>> getIntroduces(PageParam pageParam) {
        log.info("pageParam= {}, {}, {}", pageParam.getPage(), pageParam.getSize(), pageParam.getField());

//        List<String> validFields = Arrays.asList("게임", "기획", "모바일", "블록체인", "서비스", "스터디", "웹", "코테/알고리즘", "프로젝트", "AI/머신러닝", "UI/UX");
//
//        if (!validFields.contains(pageParam.getField())) {
//            return BaseResponseDto.builder().msg("유효하지 않는 분야입니다.").build();
//        }

        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());

        Page<Introduce> introducePage = introduceRepository.findByField(pageParam.getField(), pageable);

        List<IntroduceResponseDto> introduceList = introducePage.stream()
                .map(IntroduceResponseDto::new)
                .collect(Collectors.toList());

        PageDto<List<IntroduceResponseDto>> response = new PageDto<>(introducePage.hasNext(), introduceList);
        return BaseResponseDto.builder().msg("조회").data(response).build();
    }

    //<모집글 작성>
    @Transactional
    public BaseResponseDto createIntroduce(IntroduceRequestDto requestDto, UserDetailsImpl userDetails) {

        Introduce newIntroduce = new Introduce(requestDto, userDetails);
        newIntroduce = introduceRepository.save(newIntroduce);

        return BaseResponseDto.builder().msg("생성 성공").data(newIntroduce).build();
    }

    //<모집글 수정>
    @Transactional
    public BaseResponseDto<?> updatedIntroduce(Long id, IntroduceRequestDto requestDto, UserDetailsImpl userDetails) {
        Introduce savedIntroduce = findById(id);

        if (!hasPermission(userDetails, savedIntroduce)) {
            throw new RuntimeException("해당 모집공고에 관한 수정 권한이 없습니다");
        }
        savedIntroduce.update(requestDto);

        return BaseResponseDto.builder().msg("수정 성공").data(savedIntroduce).build();
    }
    //<모집글 삭제>

    @Transactional
    public StatusResponseDto deleteIntroduce(Long id, UserDetailsImpl userDetails){
        Introduce targetIntroduce = findById(id);

        if (!hasPermission(userDetails, targetIntroduce)) {
            throw new RuntimeException("해당 모집공고에 관한 삭제 권한이 없습니다");
        }
        introduceRepository.delete(targetIntroduce);
        return new StatusResponseDto("삭제 성공", HttpStatus.NO_CONTENT.value());
    }

    public Introduce findById(Long id) {
        return introduceRepository.findById(id).orElseThrow(() ->
                new IntroduceNotFoundException("해당 모집공고가 없습니다."));
    }

    public boolean hasPermission(UserDetailsImpl userDetails, Introduce introduce) {
        return introduce.getUserId().equals(userDetails.getUser().getId()) ||
                userDetails.getAuthorities().contains(new SimpleGrantedAuthority(AuthGlobalUserEnum.ADMIN.getAuthority()));
    }
}



