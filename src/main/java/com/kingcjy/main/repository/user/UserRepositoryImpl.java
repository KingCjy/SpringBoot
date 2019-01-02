package com.kingcjy.main.repository.user;

import com.kingcjy.main.entity.QUserEntity;
import com.kingcjy.main.entity.UserEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryQueryDsl {
    public UserRepositoryImpl() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity selectOneByPhoneNumber(String phoneNumber) {
        UserEntity userEntity = from(QUserEntity.userEntity)
                .where(QUserEntity.userEntity.phoneNumber.eq(phoneNumber))
                .fetchOne();
        return userEntity;
    }
}
