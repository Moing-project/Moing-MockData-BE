package com.example.finalteammockdata.domain.workspace.service;

import com.example.finalteammockdata.domain.auth.dto.AuthWorkSoloResponseDto;
import com.example.finalteammockdata.domain.auth.repository.AuthRepository;
import com.example.finalteammockdata.domain.workspace.dto.WorkCreateRequestDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkListResponseDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkRequestDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkResponseDto;
import com.example.finalteammockdata.domain.workspace.entity.WorkStack;
import com.example.finalteammockdata.domain.workspace.entity.WorkTeam;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.domain.workspace.repository.WorkRepository;
import com.example.finalteammockdata.domain.workspace.repository.WorkStackRepository;
import com.example.finalteammockdata.domain.workspace.repository.WorkTeamRepository;
import com.example.finalteammockdata.global.dto.BaseResponseDto;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.domain.workspace.enums.WorkStackEnum;
import com.example.finalteammockdata.global.exception.ErrorCodeException;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.finalteammockdata.global.enums.AccessCode.*;
import static com.example.finalteammockdata.global.enums.DeniedCode.*;

@Service
public class WorkService {

    private final WorkRepository workRepository;
    private final AuthRepository authRepository;
    private final WorkServiceHelper workServiceHelper;
    private final WorkTeamRepository workTeamRepository;
    private final WorkStackRepository workStackRepository;


    public WorkService(WorkRepository workRepository, AuthRepository authRepository, WorkServiceHelper workServiceHelper, WorkTeamRepository workTeamRepository, WorkStackRepository workStackRepository) {
        this.workRepository = workRepository;
        this.authRepository = authRepository;
        this.workServiceHelper = workServiceHelper;
        this.workTeamRepository = workTeamRepository;
        this.workStackRepository = workStackRepository;
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public MessageResponseDto createWorkspace(WorkCreateRequestDto createDto, Long userId) {
        Workspace newWorkspace = new Workspace(createDto);

        newWorkspace = workRepository.save(newWorkspace);

        if (!workspaceAddUser(newWorkspace.getId(), userId)) {
            throw ErrorCodeException.make(WORKSPACE_CREATE_ERROR.status(), WORKSPACE_CREATE_ERROR.code());
        }
        if (!workspaceAddStacks(newWorkspace.getId(), createDto.stacks())) {
            throw ErrorCodeException.make(WORKSPACE_CREATE_ERROR.status(), WORKSPACE_CREATE_ERROR.code());
        }
        return MessageResponseDto.out(WORKSPACE_CREATE_ALLOW.status(), WORKSPACE_CREATE_ALLOW.code());
    }

    public List<WorkListResponseDto> getWorkspaces() {
        List<Workspace> workspaces = workRepository.findAll();
        List<WorkListResponseDto> responseDtoList = new ArrayList<>();
        for (Workspace workspace : workspaces) {
            responseDtoList.add(getListResponseDto(workspace));
        }
        return responseDtoList;
    }

    public WorkResponseDto getWorkspace(Long workId) {
        Workspace workspace = workRepository.findById(workId).orElseThrow(() -> ErrorCodeException.make(WORKSPACE_NOT_FOUND_ERROR.status(), WORKSPACE_NOT_FOUND_ERROR.code()));
        return new WorkResponseDto(workspace, getWorkUserList(workspace.getId()));
    }

    //<워크스페이스 수정>
    // 예외사항 -> 해당 워크스페이스에 관한 관리자 권한X
    @Transactional
    public MessageResponseDto updateWorkspace(Long workId, WorkRequestDto requestDto, UserDetailsImpl userDetails){
        Workspace targetWorkspace = findByIdToWorkspace(workId);

        if(!hasPermission(userDetails, targetWorkspace)){
            throw new RuntimeException("수정 권한이 없습니다");
        }
        targetWorkspace.update(requestDto);

        if (!workspaceAddStacks(targetWorkspace.getId(), requestDto.stacks())) {
            throw ErrorCodeException.make(WORKSPACE_CREATE_ERROR.status(), WORKSPACE_CREATE_ERROR.code());
        }

        return BaseResponseDto.builder().msg("수정 성공").data(targetWorkspace).build();
    }
    //<워크스페이스 삭제>
    public MessageResponseDto deleteWorkspace(Long workId, UserDetailsImpl userDetails){
        Workspace targetWorkspace = findByIdToWorkspace(workId);
        WorkTeam targetWorkteam = findByIdToWorkteam(workId);
        WorkStack targetWorkstack = findByIdToWorkstack(workId);

        if(!hasPermission(userDetails, targetWorkspace)){
            throw new RuntimeException("삭제 권한이 없습니다");
        }
        workRepository.delete(targetWorkspace);
        workTeamRepository.delete(targetWorkteam);
        workStackRepository.delete(targetWorkstack);

        return BaseResponseDto.builder().msg("워크스페이스를 삭제하였습니다").build();
    }

    public Workspace findByIdToWorkspace(Long id){
        Workspace workspace= workRepository.findById(id).orElseThrow(()-> ErrorCodeException.make(WORKSPACE_NOT_FOUND_ERROR.status(), WORKSPACE_NOT_FOUND_ERROR.code()));
        return  workspace;
    }

    public WorkTeam findByIdToWorkteam(Long id){
        WorkTeam workteam= workTeamRepository.findById(id).orElseThrow(()-> ErrorCodeException.make(WORKSPACE_NOT_FOUND_ERROR.status(), WORKSPACE_NOT_FOUND_ERROR.code()));
        return workteam;
    }

    public WorkStack findByIdToWorkstack(Long id){
        WorkStack workstack= workStackRepository.findById(id).orElseThrow(()-> ErrorCodeException.make(WORKSPACE_NOT_FOUND_ERROR.status(), WORKSPACE_NOT_FOUND_ERROR.code()));
        return workstack;
    }

    public boolean hasPermission(UserDetailsImpl userDetails, Workspace workspace) {
        if (workTeamRepository.findByWorkIdAndUserId(workspace.getId(), userDetails.getUser().getId()).isPresent()) {
            if (userDetails.getAuthorities().equals("ROLE_ADMIN")) {
            }
            return true;
        }
        return false;
    }

    private boolean workspaceAddUser(Long workspaceId, Long userId) {
        if (workTeamRepository.findByWorkIdAndUserId(workspaceId, userId).isPresent()) {
            return false;
        }
        workTeamRepository.save(new WorkTeam(workspaceId, userId));
        return true;
    }

    private boolean workspaceAddStacks(Long workspaceId, List<String> stacks) {
        List<WorkStackEnum> workStackEnumList = new ArrayList<>();
        for (String stack : stacks) {
            WorkStackEnum workStack = WorkStackEnum.get(stack);
            if (workStack == null)
                return false;
            if (workStackRepository.findByWorkIdAndStack(workspaceId, workStack).isEmpty()) {
                workStackRepository.save(new WorkStack(workspaceId,workStack));
            }
        }
        return true;
    }

    private List<AuthWorkSoloResponseDto> getWorkUserList(Long workspaceId){
        List<AuthWorkSoloResponseDto> result = new ArrayList<>();
        for (Long userId : workTeamRepository.findAllSelectUserIdByWorkId(workspaceId)) {
            result.add(authRepository.findByProfileImageAndNicknameById(userId));
        }
        return result;
    }

    private WorkListResponseDto getListResponseDto(Workspace workspace){
        return new WorkListResponseDto(workspace.getId(),
                workspace.getImageSrc(),
                workspace.getName(),
                workStackRepository.findAllByWorkIdToStack(workspace.getId()).stream().map(WorkStackEnum::getStack).toList(),
                workspace.getIntroduce());
    }
}
