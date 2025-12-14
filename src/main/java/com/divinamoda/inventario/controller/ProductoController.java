package com.divinamoda.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divinamoda.inventario.dto.ProductoDetalleDTO;
import com.divinamoda.inventario.entity.Categoria;
import com.divinamoda.inventario.entity.Producto;
import com.divinamoda.inventario.entity.ProductoDetalle;
import com.divinamoda.inventario.repository.CategoriaRepository;
import com.divinamoda.inventario.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaRepository categoriaRepo;

    // Inyectamos ambos repositorios/servicios
    public ProductoController(ProductoService productoService, CategoriaRepository categoriaRepo) {
        this.productoService = productoService;
        this.categoriaRepo = categoriaRepo;
    }

    // CREATE
    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        // Validar que la categoría exista
        Long categoriaId = producto.getCategoria().getId();
        Categoria categoria = categoriaRepo.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        producto.setCategoria(categoria);

        return productoService.guardar(producto); 
    }

    @PostMapping("/detalles")
    public ResponseEntity<ProductoDetalle> agregarDetalle(@RequestBody ProductoDetalleDTO dto) {
        ProductoDetalle detalle = productoService.agregarDetalle(dto);
        return ResponseEntity.ok(detalle);
    }

    // READ (todos)
    @GetMapping
    public List<Producto> listarTodos() {
        return productoService.listarTodos();
    }

    // READ (por id)
    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {
        return productoService.obtenerPorId(id);
    }

    // UPDATE
    @PutMapping
    public Producto actualizar(@RequestBody Producto producto) {
        // Validar categoría si quieres
        Long categoriaId = producto.getCategoria().getId();
        Categoria categoria = categoriaRepo.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        producto.setCategoria(categoria);

        return productoService.actualizar(producto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}