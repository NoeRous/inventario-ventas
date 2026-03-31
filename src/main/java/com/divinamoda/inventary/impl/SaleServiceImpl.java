package com.divinamoda.inventary.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.divinamoda.inventary.dto.sales.SaleItemDTO;
import com.divinamoda.inventary.dto.sales.SaleItemSaleDTO;
import com.divinamoda.inventary.dto.products.ProductDetailDTO;
import com.divinamoda.inventary.dto.sales.SaleDTO;
import com.divinamoda.inventary.entity.products.Product;
import com.divinamoda.inventary.entity.products.ProductDetail;
import com.divinamoda.inventary.entity.sales.Customer;
import com.divinamoda.inventary.entity.sales.Sale;
import com.divinamoda.inventary.entity.sales.SaleItem;
import com.divinamoda.inventary.repository.CustomerRepository;
import com.divinamoda.inventary.repository.ProductDetailRepository;
import com.divinamoda.inventary.repository.ProductRepository;
import com.divinamoda.inventary.repository.SaleItemRepository;
import com.divinamoda.inventary.repository.SaleRepository;
import com.divinamoda.inventary.service.SaleService;
import jakarta.transaction.Transactional;
import com.divinamoda.inventary.enums.InventoryState;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductDetailRepository detailRepo;
    private final ProductRepository productRepository;

    public SaleServiceImpl(SaleRepository saleRepository,
            SaleItemRepository saleItemRepository,
            CustomerRepository customerRepository,
            ProductDetailRepository detailRepo,
            ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.customerRepository = customerRepository;
        this.detailRepo = detailRepo;
        this.productRepository = productRepository;
    }

    @Transactional
    public Sale saveSale(SaleDTO saleDTO) {

        Customer customer = customerRepository.findById(saleDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // 1) Validar stock de TODOS los items
        for (SaleItemDTO itemDTO : saleDTO.getSaleItems()) {
            ProductDetail productDetail = detailRepo.findById(itemDTO.getProductDetailId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (productDetail.getStock() < itemDTO.getQuantity()) {
                throw new RuntimeException(
                        "Stock insuficiente para el producto: " + productDetail.getId()
                );
            }
        }

        // 2) Crear la venta
        Sale sale = new Sale();
        sale.setType(saleDTO.getType());
        sale.setStatus(saleDTO.getStatus());
        sale.setCustomer(customer);
        sale.setSubtotal(saleDTO.getSubtotal());
        sale.setDiscount(saleDTO.getDiscount());
        sale.setTotal(saleDTO.getTotal());
        sale.setAmountPaid(saleDTO.getAmountPaid());
        sale.setPaymentMethod(saleDTO.getPaymentMethod());
        sale.setDate(LocalDateTime.now());
        sale.setActive(true);

        Sale savedSale = saleRepository.save(sale);

        // 3) Descontar stock y crear items
        for (SaleItemDTO itemDTO : saleDTO.getSaleItems()) {

            ProductDetail productDetail = detailRepo.findById(itemDTO.getProductDetailId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Descontar stock del detalle
            productDetail.setStock(productDetail.getStock() - itemDTO.getQuantity());
            detailRepo.save(productDetail);

            // Recalcular stock total del producto (suma de todos los detalles)
            Product product = productDetail.getProduct();
            Integer totalStock = detailRepo.sumStockByProductId(product.getId());
            product.setStock(totalStock);
            if (totalStock == 0) {
                    product.setInventoryState(InventoryState.AGOTADO);
                } else if (totalStock <= 2) {
                    product.setInventoryState(InventoryState.BAJO_STOCK);
                } else {
                    product.setInventoryState(InventoryState.DISPONIBLE);
                }
            
            productRepository.save(product);

            // Crear item de venta
            SaleItem saleItem = new SaleItem();
            saleItem.setSale(savedSale);
            saleItem.setProductDetail(productDetail);
            saleItem.setQuantity(itemDTO.getQuantity());
            saleItem.setUnitPrice(itemDTO.getUnitPrice());
            saleItem.setSubtotal(itemDTO.getSubtotal());

            saleItemRepository.save(saleItem);
        }

        return savedSale;
    }

    //sales 
    @Override
    public List<Sale> listAllSales(String type) {
        if (type == null || type.isBlank()) {
            return saleRepository.findAll();
        }
        return saleRepository.findByType(type);
    }

    //listar listado de iteme de venta 
    public List<SaleItemSaleDTO> getSaleItemsBySaleId(UUID saleId) {
        return saleItemRepository.findBySaleId(saleId)
                .stream()
                .map(item -> new SaleItemSaleDTO(
                        new ProductDetailDTO(
                                item.getProductDetail().getProduct().getId(),
                                item.getProductDetail().getId(),
                                item.getProductDetail().getSize(),
                                item.getProductDetail().getColor(),
                                item.getProductDetail().getWarehouse(),
                                item.getProductDetail().getStock(),
                                item.getProductDetail().getProduct()
                        ),
                        item.getId(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .toList();
    }
}
