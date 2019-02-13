package com.kingcjy.main.repository.user;

import com.kingcjy.main.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>, UserRepositoryQueryDsl{
    public UserEntity findByEmail(String email);
}
