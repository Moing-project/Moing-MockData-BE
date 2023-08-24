package com.example.finalteammockdata.domain.workspace.entity;

import com.example.finalteammockdata.domain.workspace.enums.WorkPermissionEnum;
import com.example.finalteammockdata.global.maps.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class WorkTeam extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long workId;

    private Long userId;

    private boolean favorite;

    @Enumerated(value = EnumType.STRING)
    private WorkPermissionEnum userPermission;

    public WorkTeam() {
    }

    public WorkTeam(Long workId, Long userId) {
        this.workId = workId;
        this.userId = userId;
        this.favorite = false;
        this.modifiedAt = LocalDateTime.now();
    }

    public void updateFavorite(){
        this.favorite = !this.favorite;
        this.modifiedAt = LocalDateTime.now();
    }
}
