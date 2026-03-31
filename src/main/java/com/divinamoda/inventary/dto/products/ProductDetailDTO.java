package com.divinamoda.inventary.dto.products;

import java.util.UUID;

import com.divinamoda.inventary.entity.products.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {

    private UUID productId;
    private UUID id;
    private String size;
    private String color;
    private String warehouse;
    private Integer stock;
    private Product product;
}
