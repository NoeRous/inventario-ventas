package com.divinamoda.inventary.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.divinamoda.inventary.entity.products.ProductDetail;
import com.divinamoda.inventary.enums.InventoryState;
import com.divinamoda.inventary.repository.ProductDetailRepository;
import com.divinamoda.inventary.repository.ProductRepository;
import com.divinamoda.inventary.service.ProductDetailService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository detailRepo;
    private final ProductRepository productRepository;

    // ✅ INYECTAMOS
    public ProductDetailServiceImpl(
            ProductDetailRepository detailRepo,
            ProductRepository productRepository) {
        this.detailRepo = detailRepo;
        this.productRepository = productRepository;
    }

    private void updateProductStock(UUID productId) {
        Integer totalStock = detailRepo.sumStockByProductId(productId);

        productRepository.findById(productId).ifPresent(product -> {
            product.setStock(totalStock);

            if (totalStock == 0) {
                product.setInventoryState(InventoryState.AGOTADO);
            } else if (totalStock <= 2) {
                product.setInventoryState(InventoryState.BAJO_STOCK);
            } else {
                product.setInventoryState(InventoryState.DISPONIBLE);
            }

            productRepository.save(product);
            });
    }


    @Override
    public ProductDetail addDetail(ProductDetail detail) {
        ProductDetail saved = detailRepo.save(detail);
        updateProductStock(detail.getProduct().getId());
        return saved;
    }

    @Override
    public ProductDetail updateDetail(ProductDetail detail) {
        ProductDetail updated = detailRepo.save(detail);
        updateProductStock(detail.getProduct().getId());
        return updated;
    }

    @Override
    public void delete(java.util.UUID id) {
        ProductDetail detail = detailRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Detail not found"));
        UUID productId = detail.getProduct().getId();
        detailRepo.delete(detail);
        updateProductStock(productId);
    }

}
