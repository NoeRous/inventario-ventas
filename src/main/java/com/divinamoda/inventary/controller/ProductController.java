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
import org.springframework.web.bind.annotation.RestController;

import com.divinamoda.inventary.dto.ProductDTO;
import com.divinamoda.inventary.dto.ProductDetailDTO;
import com.divinamoda.inventary.entity.Category;
import com.divinamoda.inventary.entity.Product;
import com.divinamoda.inventary.entity.ProductDetail;
import com.divinamoda.inventary.enums.InventoryState;
import com.divinamoda.inventary.repository.CategoryRepository;
import com.divinamoda.inventary.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productoService;
    private final CategoryRepository categoriaRepo;

    // Inyectamos ambos repositorios/servicios
    public ProductController(ProductService productoService, CategoryRepository categoriaRepo) {
        this.productoService = productoService;
        this.categoriaRepo = categoriaRepo;
    }

    // CREATE
    @PostMapping
    public ProductDTO crear(@RequestBody ProductDTO productoDTO) {

        // 1️⃣ Validar que la categoría exista
        UUID categoryId = productoDTO.getCategoryId();
        Category categoria = categoriaRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // 2️⃣ Crear la entidad Producto y mapear los campos desde el DTO
        Product producto = new Product();
        producto.setCode(productoDTO.getCode());
        producto.setName(productoDTO.getName());
        producto.setDescription(productoDTO.getDescription());
        producto.setPrice(productoDTO.getPrice());
        producto.setStock(productoDTO.getStock());
        producto.setImage(productoDTO.getImage());
        producto.setRating(productoDTO.getRating());
        producto.setInventoryState(
        productoDTO.getInventoryState() != null
                ? InventoryState.valueOf(productoDTO.getInventoryState())
                : InventoryState.AVAILABLE // default
);

        producto.setCategoria(categoria); // asignar la categoría válida

        // 3️⃣ Guardar la entidad
        Product productoGuardado = productoService.guardar(producto);

        // 4️⃣ Mapear la entidad guardada a DTO para enviar al frontend
        ProductDTO dtoResponse = new ProductDTO();
        //dtoResponse.setId(productoGuardado.getId());
        dtoResponse.setCode(productoGuardado.getCode());
        dtoResponse.setName(productoGuardado.getName());
        dtoResponse.setDescription(productoGuardado.getDescription());
        dtoResponse.setPrice(productoGuardado.getPrice());
        dtoResponse.setStock(productoGuardado.getStock());
        dtoResponse.setImage(productoGuardado.getImage());
        dtoResponse.setRating(productoGuardado.getRating());
        dtoResponse.setInventoryState(productoGuardado.getInventoryState().name());
        dtoResponse.setCategoryId(productoGuardado.getCategoria().getId());
       // dtoResponse.setCategoria(productoGuardado.getCategoria().getNombre());

        return dtoResponse;
    }

    @PostMapping("/details")
    public ResponseEntity<ProductDetail> agregarDetalle(@RequestBody ProductDetailDTO dto) {
        ProductDetail detalle = productoService.agregarDetalle(dto);
        return ResponseEntity.ok(detalle);
    }

    // READ (todos)
    @GetMapping
    public List<Product> listarTodos() {
        return productoService.listarTodos();
    }

    // READ (por id)
    @GetMapping("/{id}")
    public Product obtener(@PathVariable UUID id) {
        return productoService.obtenerPorId(id);
    }

    // UPDATE
    @PutMapping
    public Product actualizar(@RequestBody Product producto) {
        // Validar categoría si quieres
        UUID categoriaId = producto.getCategoria().getId();
        Category categoria = categoriaRepo.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        producto.setCategoria(categoria);

        return productoService.actualizar(producto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable UUID id) {
        productoService.eliminar(id);
    }
}