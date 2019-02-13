package com.kingcjy.main.dto;

import lombok.Data;

@Data
public class SearchProductDTO {
    private Integer id;
    private String title;
    private String supplierPrice;
    private String price;
    private String imgUrl;
}
