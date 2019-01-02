package com.kingcjy.main.entity;

import com.kingcjy.main.util.Gender;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="user")
public class UserEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String nickname;
    private String password;

    private String email;

    private String phoneNumber;
    private Integer age;
    private String birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String ci;
}