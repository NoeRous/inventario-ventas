package com.divinamoda.inventario.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductoDTO {

    private String nombre;
    private Double precio;
    private UUID categoriaId;
    
}
