package com.kingcjy.main.domain.enums;

public enum Gender {
    MALE("남자"), FEMALE("여자"), NONE("입력안함");

    final private String name;

    private Gender(String name) {
        this.name = name;
    }

    public String value() {
        return this.name;
    }
}
