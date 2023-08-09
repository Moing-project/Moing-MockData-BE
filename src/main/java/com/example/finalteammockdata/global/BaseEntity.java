package com.example.finalteammockdata.global;

import com.example.finalteammockdata.global.enums.StatusFlag;
import com.example.finalteammockdata.global.maps.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString
public abstract class BaseEntity extends Timestamped {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID id;

    @CreatedBy
    @Column(updatable = false)
    private UUID createdBy;

    @LastModifiedBy
    private UUID modifiedBy;

    private UUID eliminator;

    @Enumerated(EnumType.STRING)
    private StatusFlag delFlag = StatusFlag.NORMAL;

    public void delete() {
        this.delFlag = StatusFlag.DELETED;
    }
}
