package com.example.finalteammockdata.domain.auth.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import static com.example.finalteammockdata.domain.auth.entity.QAuthUser.authUser;

@Repository
public class QAuthRepositoryImpl implements QAuthRepository {
    private final JPAQueryFactory queryFactory;

    public QAuthRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }


    @Override
    public boolean findByUsernameExist(String username) {
        return queryFactory.select(authUser.username).from(authUser).where(authUser.username.eq(username)).fetchFirst() != null;
    }

    @Override
    public boolean findByNicknameExist(String nickname) {
        return queryFactory.select(authUser.nickname).from(authUser).where(authUser.nickname.eq(nickname)).fetchFirst() != null;
    }

    @Override
    public String findByPasswordInUsername(String username) {
        return queryFactory.select(authUser.password).from(authUser).where(authUser.username.eq(username)).fetchFirst();
    }
}
