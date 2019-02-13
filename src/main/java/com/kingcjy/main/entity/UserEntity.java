package com.kingcjy.main.entity;

import com.kingcjy.main.domain.enums.Gender;
import com.kingcjy.main.domain.enums.SocialType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="user")
@Builder
public class UserEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String principal;
    private String name;
    private String nickname;
    private String password;

    private SocialType socialType;

    private String email;

    private String phoneNumber;
    private Integer age;
    private String birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String ci;
}