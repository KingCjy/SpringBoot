package com.kingcjy.main.repository.user;

import com.kingcjy.main.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer>, UserRepositoryQueryDsl{

}
