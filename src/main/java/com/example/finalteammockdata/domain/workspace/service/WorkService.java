package com.example.finalteammockdata.domain.workspace.service;

import com.example.finalteammockdata.domain.auth.dto.AuthWorkSoloResponseDto;
import com.example.finalteammockdata.domain.auth.repository.AuthRepository;
import com.example.finalteammockdata.domain.workspace.dto.WorkCreateRequestDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkListResponseDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkResponseDto;
import com.example.finalteammockdata.domain.workspace.entity.WorkTeam;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.domain.workspace.repository.WorkRepository;
import com.example.finalteammockdata.domain.workspace.repository.WorkStackRepository;
import com.example.finalteammockdata.domain.workspace.repository.WorkTeamRepository;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.domain.workspace.enums.WorkStackEnum;
import com.example.finalteammockdata.global.exception.ErrorCodeException;
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

        if (workspaceAddUser(newWorkspace.getId(), userId)) {
            throw ErrorCodeException.make(WORKSPACE_CREATE_ERROR.getStatus(), WORKSPACE_CREATE_ERROR.getName());
        }
        if (workspaceAddStacks(newWorkspace.getId(), createDto.stacks())) {
            throw ErrorCodeException.make(WORKSPACE_CREATE_ERROR.getStatus(), WORKSPACE_CREATE_ERROR.getName());
        }
        return MessageResponseDto.out(WORKSPACE_CREATE_ALLOW.getStatus(), WORKSPACE_CREATE_ALLOW.getName());
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
        Workspace workspace = workRepository.findById(workId).orElseThrow(() -> ErrorCodeException.make(WORKSPACE_NOT_FOUND_ERROR.getStatus(), WORKSPACE_NOT_FOUND_ERROR.getName()));
        return new WorkResponseDto(workspace, getWorkUserList(workspace.getId()));
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
            if (workStackRepository.findByWorkIdAndStacks(workspaceId, stack).isEmpty()) {
                WorkStackEnum workStackEnum = WorkStackEnum.get(stack);
                if (workStackEnum != null)
                    workStackEnumList.add(workStackEnum);
            }
        }
        return workStackEnumList.size() != 0;
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
