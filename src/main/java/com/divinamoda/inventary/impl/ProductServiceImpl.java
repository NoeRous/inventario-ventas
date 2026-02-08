package com.divinamoda.inventary.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.divinamoda.inventary.dto.ProductDetailDTO;
import com.divinamoda.inventary.entity.Product;
import com.divinamoda.inventary.entity.ProductDetail;
import com.divinamoda.inventary.exception.BadRequestException;
import com.divinamoda.inventary.repository.ProductDetailRepository;
import com.divinamoda.inventary.repository.ProductRepository;
import com.divinamoda.inventary.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository detailRepo;

    // ✅ INYECTAMOS LOS DOS
    public ProductServiceImpl(ProductRepository productRepository,
            ProductDetailRepository detailRepo) {
        this.productRepository = productRepository;
        this.detailRepo = detailRepo;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product obtenerPorId(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public Product updateProduct(Product product) {
        Product exist = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        exist.setName(product.getName());
        exist.setDescription(product.getDescription());
        exist.setPrice(product.getPrice());
        exist.setStock(product.getStock());
        exist.setInventoryState(product.getInventoryState());
        exist.setCategory(product.getCategory());
        return productRepository.save(exist);
    }

    @Override
    public void delete(UUID id) {
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(
                "No se puede eliminar el producto porque tiene detalles asociados(Elimine primero los detalles)"
            );
        }
    }

    // ✅ MÉTODO PARA AGREGAR DETALLE
    @Override
    public ProductDetail addDetail(ProductDetailDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ProductDetail detail = new ProductDetail();
        detail.setProduct(product);
        detail.setSize(dto.getSize());
        detail.setColor(dto.getColor());
        detail.setStock(dto.getStock());
        detail.setWarehouse(dto.getWarehouse());
        return detailRepo.save(detail);
    }

    @Override
    public Product updateProductImage(UUID productId, MultipartFile file) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }

        try {
            // 📁 Carpeta uploads/products (relativa al proyecto)
            Path uploadPath = Paths.get("uploads");

            // Crear carpetas si no existen
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 📄 Obtener extensión segura
            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // 🆔 Nombre único
            String fileName = UUID.randomUUID().toString() + extension;

            // 💾 Guardar archivo
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING);

            // 🗂 Guardar ruta relativa en BD
            product.setImage("/uploads/" + fileName);

            return productRepository.save(product);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar imagen", e);
        }
    }

    //✅ MÉTODO PARA OBTENER DETALLES DE UN PRODUCTO
    @Override
    public List<ProductDetail> getProductDetails(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado")); 
        return detailRepo.findByProduct(product);   
    }

}
