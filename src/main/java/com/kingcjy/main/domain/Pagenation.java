package com.kingcjy.main.domain;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Pagenation<T> {
    private Integer size;
    private Integer currentPage;
    private Integer maxPage;
    private ArrayList<T> list;
}
