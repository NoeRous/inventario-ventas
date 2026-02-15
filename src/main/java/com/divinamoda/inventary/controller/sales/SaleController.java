package com.divinamoda.inventary.controller.sales;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.divinamoda.inventary.dto.sales.AvailableProductDTO;
import com.divinamoda.inventary.repository.ProductDetailRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final ProductDetailRepository productDetailRepository;

    @GetMapping("/available-products")
    public List<AvailableProductDTO> getAvailableProductsForSale() {
        return productDetailRepository.findAvailableProductsForSale();
    }
}
