package com.example.finalteammockdata.domain.team.service;

import com.example.finalteammockdata.domain.auth.dto.AuthWorkSoloResponseDto;
import com.example.finalteammockdata.domain.auth.repository.AuthRepository;
import com.example.finalteammockdata.domain.team.dto.TeamMainResponseDto;
import com.example.finalteammockdata.domain.team.dto.TeamOneResponseDto;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.domain.workspace.enums.WorkStackEnum;
import com.example.finalteammockdata.domain.workspace.repository.WorkRepository;
import com.example.finalteammockdata.domain.workspace.repository.WorkStackRepository;
import com.example.finalteammockdata.domain.workspace.repository.WorkTeamRepository;
import com.example.finalteammockdata.global.enums.DeniedCode;
import com.example.finalteammockdata.global.exception.DeniedCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TeamService {
    private final AuthRepository authRepository;
    private final WorkRepository workRepository;
    private final WorkTeamRepository workTeamRepository;
    private final WorkStackRepository workStackRepository;


    public TeamService(AuthRepository authRepository, WorkRepository workRepository, WorkTeamRepository workTeamRepository, WorkStackRepository workStackRepository) {
        this.authRepository = authRepository;
        this.workRepository = workRepository;
        this.workTeamRepository = workTeamRepository;
        this.workStackRepository = workStackRepository;
    }

    public List<TeamMainResponseDto> getTeamMain() {
        List<Workspace> workspaceList = workRepository.findAllOrderByAllowTypeToSort();
        List<TeamMainResponseDto> responseDtoList = new ArrayList<>();
        for (Workspace workspace : workspaceList) {
            List<String> stacks = workStackRepository.findAllByWorkIdToStack(workspace.getId()).stream().map(WorkStackEnum::getStack).toList();
            responseDtoList.add(new TeamMainResponseDto(workspace.getImageSrc(), workspace.getName(),stacks,workspace.getIntroduce(),workspace.getLastTime()));
        }
        return responseDtoList;
    }

    public TeamOneResponseDto getTeamOne(Long workId) {
        Workspace workspace = workRepository.findById(workId).orElseThrow(() -> DeniedCodeException.out(DeniedCode.WORKSPACE_NOT_FOUND_ERROR));
        List<AuthWorkSoloResponseDto> authWorkSoloResponseDtoList = workTeamRepository
                .findAllSelectUserIdByWorkId(workId)
                .stream()
                .map(authRepository::findByProfileImageAndNicknameById)
                .map(AuthWorkSoloResponseDto::new)
                .toList();
        List<String> stacks = workStackRepository.findAllByWorkIdToStack(workId).stream().map(WorkStackEnum::getStack).toList();
        return new TeamOneResponseDto(
                workspace.getImageSrc(),
                workspace.getAllowType().getType(),
                workspace.getLastTime(),
                workspace.getTotalMember(),
                authWorkSoloResponseDtoList,
                workspace.getIntroduce(),
                stacks);
    }
}
