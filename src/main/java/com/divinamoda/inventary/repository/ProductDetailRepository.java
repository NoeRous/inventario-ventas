package com.divinamoda.inventary.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.divinamoda.inventary.dto.sales.AvailableProductDTO;
import com.divinamoda.inventary.entity.products.Product;
import com.divinamoda.inventary.entity.products.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, UUID> {

    List<ProductDetail> findByProduct(Product product);

    @Query("""
        SELECT COALESCE(SUM(d.stock), 0)
        FROM ProductDetail d
        WHERE d.product.id = :productId
    """)
    Integer sumStockByProductId(@Param("productId") UUID productId);

    @Query("""
    SELECT new com.divinamoda.inventary.dto.sales.AvailableProductDTO(
        c.name,
        p.name,
        p.image,
        p.price,
        pd.id,
        pd.color,
        pd.size,
        pd.stock,
        pd.warehouse
    )
    FROM ProductDetail pd
    JOIN pd.product p
    JOIN p.category c
    WHERE p.inventoryState IN ('BAJO_STOCK', 'DISPONIBLE')
    ORDER BY p.name
    """)
    List<AvailableProductDTO> findAvailableProductsForSale();

}
