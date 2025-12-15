package com.divinamoda.inventario.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.divinamoda.inventario.dto.ProductoDetalleDTO;
import com.divinamoda.inventario.entity.Producto;
import com.divinamoda.inventario.entity.ProductoDetalle;
import com.divinamoda.inventario.repository.ProductoDetalleRepository;
import com.divinamoda.inventario.repository.ProductoRepository;
import com.divinamoda.inventario.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoDetalleRepository detalleRepo;

    // ✅ INYECTAMOS LOS DOS
    public ProductoServiceImpl(ProductoRepository productoRepository,
                               ProductoDetalleRepository detalleRepo) {
        this.productoRepository = productoRepository;
        this.detalleRepo = detalleRepo;
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public Producto actualizar(Producto producto) {
        Producto existente = productoRepository.findById(producto.getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existente.setNombre(producto.getNombre());
        existente.setPrecio(producto.getPrecio());
        existente.setStock(producto.getStock());

        return productoRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    // ✅ MÉTODO PARA AGREGAR DETALLE
    @Override
    public ProductoDetalle agregarDetalle(ProductoDetalleDTO dto) {

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ProductoDetalle detalle = new ProductoDetalle();
        detalle.setProducto(producto);
        detalle.setTalla(dto.getTalla());
        detalle.setColor(dto.getColor());
        detalle.setStock(dto.getStock());
        detalle.setAlmacen(dto.getAlmacen());

        return detalleRepo.save(detalle);
    }
}
