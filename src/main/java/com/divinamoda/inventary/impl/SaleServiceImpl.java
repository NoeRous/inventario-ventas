package com.divinamoda.inventary.impl;

import org.springframework.stereotype.Service;
import com.divinamoda.inventary.dto.sales.SaleItemDTO;
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

    @Override
    public Sale saveSale(SaleDTO sale) {

        Sale saleEntity = new Sale();
        saleEntity.setType(sale.getType());
        saleEntity.setStatus(sale.getStatus());
        saleEntity.setSubtotal(sale.getSubtotal());
        saleEntity.setDiscount(sale.getDiscount());
        saleEntity.setTotal(sale.getTotal());
        saleEntity.setAmountPaid(sale.getAmountPaid());
        saleEntity.setPaymentMethod(sale.getPaymentMethod());

        // 🔹 Buscar cliente
        Customer customer = customerRepository.findById(sale.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        saleEntity.setCustomer(customer);
        saleEntity.setDate(java.time.LocalDateTime.now());

        Sale savedSale = saleRepository.save(saleEntity);

        // 🔹 Guardar items
        for (SaleItemDTO item : sale.getSaleItems()) {

            ProductDetail productDetail = detailRepo.findById(item.getProductDetailId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            /*
             * if (productDetail.getStock() < item.getQuantity()) {
             * throw new RuntimeException("Stock insuficiente");
             * }
             */

            productDetail.setStock(productDetail.getStock() - item.getQuantity());
            this.detailRepo.save(productDetail);

            Product product = productRepository.findById(productDetail.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            product.setStock(product.getStock() - item.getQuantity());
            this.productRepository.save(product);

            SaleItem saleItemEntity = new SaleItem();
            saleItemEntity.setSale(savedSale);
            saleItemEntity.setProductDetail(productDetail);
            saleItemEntity.setQuantity(item.getQuantity());
            saleItemEntity.setUnitPrice(item.getUnitPrice());
            saleItemEntity.setSubtotal(item.getSubtotal());

            saleItemRepository.save(saleItemEntity);
        }

        return savedSale;
    }
}
