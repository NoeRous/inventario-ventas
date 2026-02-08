package com.divinamoda.inventary.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divinamoda.inventary.entity.Product;
import com.divinamoda.inventary.entity.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, UUID> {

    List<ProductDetail> findByProduct(Product product);
}
