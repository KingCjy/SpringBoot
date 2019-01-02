package com.kingcjy.main.service;

import com.kingcjy.main.dto.UserDto;
import com.kingcjy.main.entity.UserEntity;
import com.kingcjy.main.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserEntity getUserByPhone(String s) {
        return userRepository.selectOneByPhoneNumber(s);
    }

    public void join(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setNickname(userDto.getNickname());
        user.setPassword(userDto.getPassword());

    }
}
