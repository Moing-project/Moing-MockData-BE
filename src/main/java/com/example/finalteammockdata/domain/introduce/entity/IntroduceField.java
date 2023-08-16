package com.example.finalteammockdata.domain.introduce.entity;

import com.example.finalteammockdata.domain.introduce.enums.IntroduceFieldEnum;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class IntroduceField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long fieldId;

    @Enumerated(value=EnumType.STRING)
    IntroduceFieldEnum field;

    public IntroduceField(){

    }

    public IntroduceField(Long fieldId, IntroduceFieldEnum field ){
        this.fieldId = fieldId;
        this.field = field;
    }

}
