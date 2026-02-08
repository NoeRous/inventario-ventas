package com.divinamoda.inventary.dto;

import java.util.UUID;

import com.divinamoda.inventary.entity.Product;

import lombok.Data;

@Data
public class ProductDetailDTO {

    private UUID productId;
    private UUID id;
    private String size;
    private String color;
    private String warehouse;
    private Integer stock;
    private Product product;
}
