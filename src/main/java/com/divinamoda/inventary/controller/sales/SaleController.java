package com.divinamoda.inventary.controller.sales;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divinamoda.inventary.dto.sales.AvailableProductDTO;
import com.divinamoda.inventary.dto.sales.SaleDTO;
import com.divinamoda.inventary.entity.sales.Sale;
import com.divinamoda.inventary.repository.ProductDetailRepository;
import com.divinamoda.inventary.service.CustomerService;
import com.divinamoda.inventary.service.SaleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final ProductDetailRepository productDetailRepository;
    private final SaleService saleService;

    public SaleController(SaleService saleService,
            ProductDetailRepository productDetailRepository) {
        this.saleService = saleService;
        this.productDetailRepository = productDetailRepository;
    }

    @GetMapping("/available-products")
    public List<AvailableProductDTO> getAvailableProductsForSale() {
        return productDetailRepository.findAvailableProductsForSale();
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody SaleDTO saleDTO) {
        return saleService.saveSale(saleDTO) != null
                ? ResponseEntity.ok(saleService.saveSale(saleDTO))
                : ResponseEntity.badRequest().build();
    }
}
