package com.divinamoda.inventary.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.divinamoda.inventary.entity.Product;
import com.divinamoda.inventary.entity.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, UUID> {

    List<ProductDetail> findByProduct(Product product);

    @Query("""
        SELECT COALESCE(SUM(d.stock), 0)
        FROM ProductDetail d
        WHERE d.product.id = :productId
    """)
    Integer sumStockByProductId(@Param("productId") UUID productId);
}
