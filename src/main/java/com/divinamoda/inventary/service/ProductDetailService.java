package com.divinamoda.inventary.service;
import java.util.UUID;

import com.divinamoda.inventary.entity.products.ProductDetail;

public interface ProductDetailService {

    ProductDetail addDetail(ProductDetail detail);
    ProductDetail updateDetail(ProductDetail detail);
    void delete(UUID id);
    
}
