package com.divinamoda.inventary.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.divinamoda.inventary.dto.ProductDTO;
import com.divinamoda.inventary.dto.ProductDetailDTO;
import com.divinamoda.inventary.entity.Category;
import com.divinamoda.inventary.entity.Product;
import com.divinamoda.inventary.entity.ProductDetail;
import com.divinamoda.inventary.enums.InventoryState;
import com.divinamoda.inventary.exception.BadRequestException;
import com.divinamoda.inventary.exception.ResourceNotFoundException;
import com.divinamoda.inventary.repository.CategoryRepository;
import com.divinamoda.inventary.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepo;
    private static final Logger log =
            LoggerFactory.getLogger(ProductController.class);

    // Inyectamos ambos repositorios/servicios
    public ProductController(ProductService productService, CategoryRepository categoryRepo) {
        this.productService = productService;
        this.categoryRepo = categoryRepo;
    }

    // CREATE
    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        log.info("🟢 Creando producto: {}", productDTO.getName());

        // 1️⃣ Validar que la categoría exista
        UUID categoryId = productDTO.getCategoryId();
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("❌ Categoría no encontrada: {}", categoryId);
                    return new RuntimeException("Categoría no encontrada");
                });

        // 2️⃣ Crear la entidad Producto
        Product product = new Product();
        product.setCode(productDTO.getCode());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock() != null ? productDTO.getStock() : 0);
        product.setImage(productDTO.getImage());
        product.setRating(productDTO.getRating() != null ? productDTO.getRating() : 5);

        InventoryState state = productDTO.getInventoryState() != null
                ? InventoryState.valueOf(productDTO.getInventoryState())
                : InventoryState.DISPONIBLE;

        product.setInventoryState(state);
        product.setCategory(category);

        log.debug("📦 Producto a guardar: code={}, price={}, stock={}, state={}",
                product.getCode(),
                product.getPrice(),
                product.getStock(),
                product.getInventoryState());

        // 3️⃣ Guardar
        Product productSave = productService.saveProduct(product);

        log.info("✅ Producto creado con ID: {}", productSave.getId());

        // 4️⃣ Mapear a DTO
        ProductDTO dtoResponse = new ProductDTO();
        dtoResponse.setCode(productSave.getCode());
        dtoResponse.setName(productSave.getName());
        dtoResponse.setDescription(productSave.getDescription());
        dtoResponse.setPrice(productSave.getPrice());
        dtoResponse.setStock(productSave.getStock());
        dtoResponse.setImage(productSave.getImage());
        dtoResponse.setRating(productSave.getRating());
        dtoResponse.setInventoryState(productSave.getInventoryState().name());
        dtoResponse.setCategoryId(productSave.getCategory().getId());

        return dtoResponse;
    }

    @PostMapping("/details")
    public ResponseEntity<ProductDetail> addDetail(@RequestBody ProductDetailDTO dto) {
        ProductDetail detail = productService.addDetail(dto);
        return ResponseEntity.ok(detail);
    }

    // READ (todos)
    @GetMapping
    public List<Product> listAllProducts() {
        return productService.listAllProducts();
    }

    // READ (por id)
    @GetMapping("/{id}")
    public Product obtener(@PathVariable UUID id) {
        return productService.obtenerPorId(id);
    }

    // UPDATE
   @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductDTO dto) {

        // Validar categoría
        if (dto.getCategoryId() == null) {
            throw new BadRequestException("La categoría es obligatoria");
        }

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Categoría no encontrada")
                );

        // Obtener producto existente
        Product product = productService.obtenerPorId(id);

        // Actualizar campos
        product.setCode(dto.getCode());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(category);

        if (dto.getInventoryState() != null) {
            product.setInventoryState(
                    InventoryState.valueOf(dto.getInventoryState())
            );
        }

        // Imagen (solo si viene algo)
        if (dto.getImage() != null) {
            product.setImage(dto.getImage());
        }

        return productService.updateProduct(product);
    }


    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        productService.delete(id);
    }

    @PostMapping("/upload/{id}/image")
    public Product uploadProductImage(@PathVariable UUID id, @RequestParam("image") MultipartFile file) {
        System.out.println(">>> ENTRO AL ENDPOINT uploadProductImage");
        System.out.println("ID: " + id);
        return productService.updateProductImage(id, file);
        // return productService.updateProductImage(id, file);
    }

    //obtener detalles de un producto
    @GetMapping("/{id}/details")
    public List<ProductDetail> getProductDetails(@PathVariable UUID id) {
        return productService.getProductDetails(id);
    }
}