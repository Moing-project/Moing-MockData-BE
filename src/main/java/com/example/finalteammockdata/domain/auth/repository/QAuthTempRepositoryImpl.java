package com.example.finalteammockdata.domain.auth.repository;

import com.example.finalteammockdata.domain.auth.entity.QAuthTempUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.finalteammockdata.domain.auth.entity.QAuthTempUser.authTempUser;

@Repository
public class QAuthTempRepositoryImpl implements QAuthTempRepository{

    private final JPAQueryFactory queryFactory;

    public QAuthTempRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    @Override
    @Transactional
    public Long deleteByEmail(String email) {

        return queryFactory.delete(authTempUser).where(authTempUser.email.eq(email)).execute();
    }
}
