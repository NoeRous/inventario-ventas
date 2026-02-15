package com.divinamoda.inventary.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divinamoda.inventary.entity.products.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
