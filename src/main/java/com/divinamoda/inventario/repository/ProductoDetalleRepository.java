package com.divinamoda.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divinamoda.inventario.entity.ProductoDetalle;

public interface ProductoDetalleRepository extends JpaRepository<ProductoDetalle, Long> {
}
