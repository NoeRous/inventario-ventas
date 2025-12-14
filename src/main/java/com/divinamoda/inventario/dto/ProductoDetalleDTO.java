package com.divinamoda.inventario.dto;

import lombok.Data;

@Data
public class ProductoDetalleDTO {

    private Long productoId;
    private String talla;
    private String color;
    private Integer stock;
    
}
