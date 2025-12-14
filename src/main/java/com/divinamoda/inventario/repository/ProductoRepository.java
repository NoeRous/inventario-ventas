package com.divinamoda.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divinamoda.inventario.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
