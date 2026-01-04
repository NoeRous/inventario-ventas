package com.divinamoda.inventary.service;

import java.util.List;
import java.util.UUID;

import com.divinamoda.inventary.dto.ProductDetailDTO;
import com.divinamoda.inventary.entity.Product;
import com.divinamoda.inventary.entity.ProductDetail;

public interface ProductService {

    Product guardar(Product producto);
    List<Product> listarTodos();
    Product obtenerPorId(UUID id);
    Product actualizar(Product producto);
    void eliminar(UUID id);

    ProductDetail agregarDetalle(ProductDetailDTO dto);
}
