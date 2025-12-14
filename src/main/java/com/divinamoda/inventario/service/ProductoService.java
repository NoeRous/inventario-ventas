package com.divinamoda.inventario.service;

import java.util.List;

import com.divinamoda.inventario.dto.ProductoDetalleDTO;
import com.divinamoda.inventario.entity.Producto;
import com.divinamoda.inventario.entity.ProductoDetalle;

public interface ProductoService {

    Producto guardar(Producto producto);
    List<Producto> listarTodos();
    Producto obtenerPorId(Long id);
    Producto actualizar(Producto producto);
    void eliminar(Long id);

    ProductoDetalle agregarDetalle(ProductoDetalleDTO dto);
}
