package com.divinamoda.inventary.dto.sales;

import java.util.UUID;

import lombok.Data;

@Data
public class AvailableProductDTO {

    private String categoryName;
    private String productName;
    private String image;
    private Double price; // Cambiado de BigDecimal a Double
    private UUID productDetailId;
    private String color;
    private String size;
    private Integer stock;
    private String warehouse;

    // 🔥 ESTE CONSTRUCTOR ES OBLIGATORIO PARA JPQL
    public AvailableProductDTO(
            String categoryName,
            String productName,
            String image,
            Double price, // Cambiado de BigDecimal a Double
            UUID productDetailId,
            String color,
            String size,
            Integer stock,
            String warehouse
    ) {
        this.categoryName = categoryName;
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.productDetailId = productDetailId;
        this.color = color;
        this.size = size;
        this.stock = stock;
        this.warehouse = warehouse;
    }

    // getters (o Lombok @Getter)
}
