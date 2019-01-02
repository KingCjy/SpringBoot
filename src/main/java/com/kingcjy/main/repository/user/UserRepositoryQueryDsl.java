package com.kingcjy.main.repository.user;

import com.kingcjy.main.entity.UserEntity;

public interface UserRepositoryQueryDsl  {
    public UserEntity selectOneByPhoneNumber(String phoneNumber);
}
