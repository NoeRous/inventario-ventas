package com.divinamoda.inventary.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.divinamoda.inventary.dto.ProductDetailDTO;
import com.divinamoda.inventary.entity.Product;
import com.divinamoda.inventary.entity.ProductDetail;

public interface ProductService {

    Product saveProduct(Product product);
    List<Product> listAllProducts();
    Product obtenerPorId(UUID id);
    Product updateProduct(Product product);
    void eliminar(UUID id);
    Product updateProductImage(UUID productId, MultipartFile file);

    ProductDetail addDetail(ProductDetailDTO dto);
    List<ProductDetail> getProductDetails(UUID productId);
}
