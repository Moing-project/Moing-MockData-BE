package com.example.finalteammockdata.domain.social.entity;

import com.example.finalteammockdata.domain.auth.dto.ReqMemberInfoDto;
import com.example.finalteammockdata.domain.social.dto.SocialUserInfoDto;
import com.example.finalteammockdata.global.BaseEntity;
import com.example.finalteammockdata.global.enums.Role;
import com.example.finalteammockdata.global.enums.Social;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Column(unique = true, length = 50)
    private String loginId;

    @Enumerated(EnumType.STRING)
    private Social social;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    private String url;

    @Column(columnDefinition = "LONGTEXT")
    private String aboutMe;

    @Column(columnDefinition = "LONGTEXT")
    private String profileImage;

    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

    @Enumerated(EnumType.STRING)
    private Role role;



    @Builder
    public Member(String loginId, Social social, String password, String nickname, String url, String aboutMe,
                  String profileImage, LocalDateTime loginTime, LocalDateTime logoutTime, Role role) {
        this.loginId = loginId;
        this.social = social;
        this.password = password;
        this.nickname = nickname;
        this.url = url;
        this.aboutMe = aboutMe;
        this.profileImage = profileImage;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.role = role;
    }
    //fixme 소셜로그인 전용임으로 password 불필요하나,
    // 스프링시큐리티 특성상 암호화된 password 를 넣어야한다. updatePassword()를 통해 암호화된 비밀번호를 넣자.
    // 추후 일반 회원가입 기능이 생긴다면, 패스워드 검증 룰을 재정의 하여야한다.

    public Member(SocialUserInfoDto socialUserInfoDto) {
        this.loginId = socialUserInfoDto.getLoginId();
        this.nickname = socialUserInfoDto.getNickname();
        this.profileImage = socialUserInfoDto.getProfileImage();
        this.social = socialUserInfoDto.getSocial();
        this.role = Role.ROLE_USER;
//        this.password = socialUserInfoDto.getLoginId() + socialUserInfoDto.getSocial().toString();
    }

    public void updateMamber(ReqMemberInfoDto reqMemberInfoDto) {

        this.profileImage = reqMemberInfoDto.getProfileImage();
        this.nickname = reqMemberInfoDto.getNickname();
        this.url = reqMemberInfoDto.getUrl();
        this.aboutMe = reqMemberInfoDto.getAboutMe();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateLoginTime(LocalDateTime currentTime) {
        this.loginTime = currentTime;
    }

    public void updateLogoutTime(LocalDateTime currentTime) {
        this.logoutTime = currentTime;
    }

