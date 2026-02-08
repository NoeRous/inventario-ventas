package com.divinamoda.inventary.impl;

import org.springframework.stereotype.Service;
import com.divinamoda.inventary.entity.ProductDetail;
import com.divinamoda.inventary.repository.ProductDetailRepository;
import com.divinamoda.inventary.service.ProductDetailService;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository detailRepo;

    // ✅ INYECTAMOS
    public ProductDetailServiceImpl(
            ProductDetailRepository detailRepo) {
        this.detailRepo = detailRepo;
    }

    @Override
    public ProductDetail addDetail(ProductDetail detail) {
        return detailRepo.save(detail);
    }

    @Override
    public ProductDetail updateDetail(ProductDetail detail) {
        // TODO Auto-generated method stub
        return detailRepo.save(detail);

    }

}
