package com.divinamoda.inventario.dto;

import lombok.Data;

@Data
public class ProductoDTO {

    private String nombre;
    private Double precio;
    private Long categoriaId;
    
}
