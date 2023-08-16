package com.example.finalteammockdata.domain.workspace.service;

import com.example.finalteammockdata.domain.workspace.dto.WorkCreateDto;
import com.example.finalteammockdata.domain.workspace.dto.WorkListResponseDto;
import com.example.finalteammockdata.domain.workspace.entity.WorkTeam;
import com.example.finalteammockdata.domain.workspace.entity.Workspace;
import com.example.finalteammockdata.domain.workspace.repository.WorkRepository;
import com.example.finalteammockdata.domain.workspace.repository.WorkStackRepository;
import com.example.finalteammockdata.domain.workspace.repository.WorkTeamRepository;
import com.example.finalteammockdata.global.dto.MessageResponseDto;
import com.example.finalteammockdata.global.enums.WorkspaceStack;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkService {

    private final WorkRepository workRepository;
    private final WorkServiceHelper workServiceHelper;
    private final WorkTeamRepository workTeamRepository;
    private final WorkStackRepository workStackRepository;

    public WorkService(WorkRepository workRepository, WorkServiceHelper workServiceHelper, WorkTeamRepository workTeamRepository, WorkStackRepository workStackRepository) {
        this.workRepository = workRepository;
        this.workServiceHelper = workServiceHelper;
        this.workTeamRepository = workTeamRepository;
        this.workStackRepository = workStackRepository;
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public MessageResponseDto createWorkspace(WorkCreateDto createDto, Long userId) {
        Workspace newWorkspace = new Workspace(createDto);

        newWorkspace = workRepository.save(newWorkspace);

        if (workspaceAddUser(newWorkspace.getId(), userId)) {
            throw new RuntimeException("out");
        }
        if (workspaceAddStacks(newWorkspace.getId(), createDto.stacks())) {
            throw new RuntimeException("out");
        }
        return MessageResponseDto.out(201, "create");
    }

    private boolean workspaceAddUser(Long workspaceId, Long userId) {
        if (workTeamRepository.findByWorkIdAndUserId(workspaceId, userId).isPresent()) {
            return false;
        }
        workTeamRepository.save(new WorkTeam(workspaceId, userId));
        return true;
    }

    private boolean workspaceAddStacks(Long workspaceId, List<String> stacks) {
        List<WorkspaceStack> workspaceStackList = new ArrayList<>();
        for (String stack : stacks) {
            if (workStackRepository.findByWorkIdAndStacks(workspaceId, stack).isEmpty()) {
                WorkspaceStack workspaceStack = WorkspaceStack.get(stack);
                if (workspaceStack != null)
                    workspaceStackList.add(workspaceStack);
            }
        }
        return workspaceStackList.size() != 0;
    }

    public List<WorkListResponseDto> getWorkspaces() {
        List<Workspace> workspaces = workRepository.findAll();
        List<WorkListResponseDto> responseDtoList = new ArrayList<>();
        for (Workspace workspace : workspaces) {
            responseDtoList.add(getListResponseDto(workspace));
        }
        return responseDtoList;
    }

    private WorkListResponseDto getListResponseDto(Workspace workspace){
        return new WorkListResponseDto(workspace.getImageSrc(),
                workspace.getName(),
                workStackRepository.findAllByWorkIdToStack(workspace.getId()).stream().map(WorkspaceStack::getStack).toList(),
                workspace.getIntroduce());
    }
}
