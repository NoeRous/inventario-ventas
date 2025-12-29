package com.divinamoda.inventario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divinamoda.inventario.entity.ProductoDetalle;

public interface ProductoDetalleRepository extends JpaRepository<ProductoDetalle, UUID> {
}
