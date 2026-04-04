package com.divinamoda.inventary.controller.sales;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divinamoda.inventary.dto.sales.AvailableProductDTO;
import com.divinamoda.inventary.dto.sales.SaleDTO;
import com.divinamoda.inventary.dto.sales.SaleDetailDTO;
import com.divinamoda.inventary.dto.sales.SaleItemSaleDTO;
import com.divinamoda.inventary.entity.sales.Sale;
import com.divinamoda.inventary.repository.ProductDetailRepository;
import com.divinamoda.inventary.service.SaleService;

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
        try {
            System.out.println("+=========**********=========saleDTO===");
            Sale savedSale = saleService.saveSale(saleDTO);
            return ResponseEntity.ok(savedSale);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // obtener ventas 
    @GetMapping()
    public List<Sale> getProductsForSale(@RequestParam(required = false) String type) {
        return saleService.listAllSales(type);
    }

    //obtener saleItems
    @GetMapping("/{saleId}/items")
    public List<SaleItemSaleDTO> getSaleItemsBySaleId(@PathVariable UUID saleId) {
        return saleService.getSaleItemsBySaleId(saleId);
    }

    @PutMapping("/{id}/detail")
    public ResponseEntity<SaleDTO> updateSaleDetail(
            @PathVariable UUID id,
            @RequestBody SaleDetailDTO detailDTO) {

        return ResponseEntity.ok(saleService.updateSaleDetail(id, detailDTO));
    }
}
