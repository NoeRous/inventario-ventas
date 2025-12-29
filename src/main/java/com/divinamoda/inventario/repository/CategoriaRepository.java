package com.divinamoda.inventario.repository;

import java.util.UUID;

import com.divinamoda.inventario.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}
