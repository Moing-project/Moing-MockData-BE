package com.example.finalteammockdata.domain.workspace.service;

import com.example.finalteammockdata.domain.auth.dto.AuthWorkSoloResponseDto;
import com.example.finalteammockdata.domain.auth.repository.AuthRepository;
import com.example.finalteammockdata.domain.workspace.dao.WorkMainDao;
import com.example.finalteammockdata.domain.workspace.dto.WorkCreateRequestDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkListResponseDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkResponseDto;
import com.example.finalteammockdata.domain.workspace.entity.WorkStack;
import com.example.finalteammockdata.domain.workspace.entity.WorkTeam;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.domain.workspace.repository.WorkRepository;
import com.example.finalteammockdata.domain.workspace.repository.WorkStackRepository;
import com.example.finalteammockdata.domain.workspace.repository.WorkTeamRepository;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.domain.workspace.enums.WorkStackEnum;
import com.example.finalteammockdata.global.enums.AuthGlobalUserEnum;
import com.example.finalteammockdata.global.exception.ErrorCodeException;
import com.example.finalteammockdata.global.sercurity.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public MessageResponseDto createWorkspace(WorkCreateRequestDto createDto, Long userId, AuthGlobalUserEnum userRating) {
        Workspace newWorkspace = new Workspace(createDto, workServiceHelper.createMaxMemberToUserRating(userRating));

        newWorkspace = workRepository.save(newWorkspace);

        if (!workspaceAddUser(newWorkspace.getId(), userId)) {
            throw ErrorCodeException.make(WORKSPACE_CREATE_ERROR.status(), WORKSPACE_CREATE_ERROR.code());
        }
        if (!workspaceAddStacks(newWorkspace.getId(), createDto.stacks())) {
            throw ErrorCodeException.make(WORKSPACE_CREATE_ERROR.status(), WORKSPACE_CREATE_ERROR.code());
        }
        return MessageResponseDto.out(WORKSPACE_CREATE_ALLOW.status(), WORKSPACE_CREATE_ALLOW.code());
    }

    public List<WorkListResponseDto> getWorkspaces(Long userId) {
        List<WorkTeam> works = workTeamRepository.findAllByUserIdOrderByModifiedAtDesc(userId);
        List<WorkMainDao> workspaces = workRepository.findAllByListWorkIdToMainDao(works);
        return workspaces.stream().map(e-> new WorkListResponseDto(e.id(), e.imageSrc(), e.title(), e.favorite())).toList();
    }

    public WorkResponseDto getWorkspace(Long workId) {
        Workspace workspace = workRepository.findById(workId).orElseThrow(() -> ErrorCodeException.make(WORKSPACE_NOT_FOUND_ERROR.status(), WORKSPACE_NOT_FOUND_ERROR.code()));
        return new WorkResponseDto(workspace, workStackRepository.findAllByWorkIdToStack(workId), getWorkUserList(workId));
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
            result.add(new AuthWorkSoloResponseDto(authRepository.findByProfileImageAndNicknameById(userId)));
        }
        return result;
    }

    private WorkListResponseDto getListResponseDto(Workspace workspace, boolean favorite){
        return new WorkListResponseDto(workspace.getId(),
                workspace.getImageSrc(),
                workspace.getTitle(),
                favorite);
    }
}
