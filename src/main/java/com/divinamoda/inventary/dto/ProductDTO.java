package com.divinamoda.inventary.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductDTO {

    //private UUID id;

    private String code;
    private String name;
    private String description;

    private Double price;
    private Integer stock;
    private String image;
    private Integer rating;

    // Se envía como String para el frontend
    private String inventoryState;

    // Relación simplificada
    private UUID categoryId;
    //private String categoria;

    
}
