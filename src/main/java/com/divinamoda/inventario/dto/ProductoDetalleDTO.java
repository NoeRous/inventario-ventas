package com.divinamoda.inventario.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductoDetalleDTO {

    private UUID productoId;
    private String talla;
    private String color;
    private String almacen;
    private Integer stock;
    
}
