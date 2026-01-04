package com.divinamoda.inventary.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductDetailDTO {

    private UUID productId;
    private String size;
    private String color;
    private String warehouse;
    private Integer stock;
}
