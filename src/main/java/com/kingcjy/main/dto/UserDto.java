package com.kingcjy.main.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserDto {

    @NotNull
    @Size(min=3, max=20)
    private String name;

    @NotNull
    @Size(min=3, max=20)
    private String nickname;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @Size(min=3, max=10)
    private String phoneNumber;

    private Integer age;

    private String birth;

    @Pattern(regexp = "[MALE|FEMALE]", message = "MALE 혹은 FEMALE 이여야 합니다.")
    private String gender;

    private String insertedAt;
    private String updatedAt;
}
