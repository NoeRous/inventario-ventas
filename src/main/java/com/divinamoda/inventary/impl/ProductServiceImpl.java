package com.divinamoda.inventary.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.divinamoda.inventary.dto.ProductDetailDTO;
import com.divinamoda.inventary.entity.Product;
import com.divinamoda.inventary.entity.ProductDetail;
import com.divinamoda.inventary.repository.ProductDetailRepository;
import com.divinamoda.inventary.repository.ProductRepository;
import com.divinamoda.inventary.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository detalleRepo;

    // ✅ INYECTAMOS LOS DOS
    public ProductServiceImpl(ProductRepository productRepository,
                               ProductDetailRepository detalleRepo) {
        this.productRepository = productRepository;
        this.detalleRepo = detalleRepo;
    }

    @Override
    public Product guardar(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> listarTodos() {
        return productRepository.findAll();
    }

    @Override
    public Product obtenerPorId(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public Product actualizar(Product product) {
        Product exist = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        exist.setName(product.getName());
        exist.setPrice(product.getPrice());
        exist.setStock(product.getStock());
        exist.setInventoryState(product.getInventoryState());
        exist.setCategoria(product.getCategoria());    
        return productRepository.save(exist);
    }

    @Override
    public void eliminar(UUID id) {
        productRepository.deleteById(id);
    }

    // ✅ MÉTODO PARA AGREGAR DETALLE
    @Override
    public ProductDetail agregarDetalle(ProductDetailDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ProductDetail detalle = new ProductDetail();
        detalle.setProduct(product);
        detalle.setSize(dto.getSize());
        detalle.setColor(dto.getColor());
        detalle.setStock(dto.getStock());
        detalle.setWarehouse(dto.getWarehouse());
        return detalleRepo.save(detalle);
    }
}
