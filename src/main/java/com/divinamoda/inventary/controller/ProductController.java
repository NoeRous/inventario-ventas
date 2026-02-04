package com.divinamoda.inventary.controller;

import java.util.List;
import java.util.UUID;

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

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepo;

    // Inyectamos ambos repositorios/servicios
    public ProductController(ProductService productService, CategoryRepository categoryRepo) {
        this.productService = productService;
        this.categoryRepo = categoryRepo;
    }

    // CREATE
    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {

        // 1️⃣ Validar que la categoría exista
        UUID categoryId = productDTO.getCategoryId();
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // 2️⃣ Crear la entidad Producto y mapear los campos desde el DTO
        Product product = new Product();
        product.setCode(productDTO.getCode());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setImage(productDTO.getImage());
        product.setRating(productDTO.getRating());
        product.setInventoryState(
                productDTO.getInventoryState() != null
                        ? InventoryState.valueOf(productDTO.getInventoryState())
                        : InventoryState.DISPONIBLE // default
        );

        product.setCategory(category); // asignar la categoría válida

        // 3️⃣ Guardar la entidad
        Product productSave = productService.saveProduct(product);

        // 4️⃣ Mapear la entidad guardada a DTO para enviar al frontend
        ProductDTO dtoResponse = new ProductDTO();
        // dtoResponse.setId(productSave.getId());
        dtoResponse.setCode(productSave.getCode());
        dtoResponse.setName(productSave.getName());
        dtoResponse.setDescription(productSave.getDescription());
        dtoResponse.setPrice(productSave.getPrice());
        dtoResponse.setStock(productSave.getStock());
        dtoResponse.setImage(productSave.getImage());
        dtoResponse.setRating(productSave.getRating());
        dtoResponse.setInventoryState(productSave.getInventoryState().name());
        dtoResponse.setCategoryId(productSave.getCategory().getId());
        // dtoResponse.setCategoria(productSave.getCategory().getNombre());

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
            @RequestBody Product product) {

        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new BadRequestException("La categoría es obligatoria");
        }

        product.setId(id);

        Category category = categoryRepo.findById(product.getCategory().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Categoría no encontrada")
                );

        product.setCategory(category);

        return productService.updateProduct(product);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable UUID id) {
        productService.eliminar(id);
    }

    @PostMapping("/upload/{id}/image")
    public Product uploadProductImage(@PathVariable UUID id, @RequestParam("image") MultipartFile file) {
        System.out.println(">>> ENTRO AL ENDPOINT uploadProductImage");
        System.out.println("ID: " + id);
        return productService.updateProductImage(id, file);
        // return productService.updateProductImage(id, file);
    }

}