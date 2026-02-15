package com.divinamoda.inventary.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.divinamoda.inventary.dto.products.ProductDetailDTO;
import com.divinamoda.inventary.entity.products.Product;
import com.divinamoda.inventary.entity.products.ProductDetail;

public interface ProductService {

    Product saveProduct(Product product);
    List<Product> listAllProducts();
    Product obtenerPorId(UUID id);
    Product updateProduct(Product product);
    void delete(UUID id);
    
    Product updateProductImage(UUID productId, MultipartFile file);

    ProductDetail addDetail(ProductDetailDTO dto);
    List<ProductDetail> getProductDetails(UUID productId);
}
