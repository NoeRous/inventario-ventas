package com.divinamoda.inventary.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.divinamoda.inventary.dto.ProductDetailDTO;
import com.divinamoda.inventary.entity.ProductDetail;
import com.divinamoda.inventary.service.ProductDetailService;

@RestController
@RequestMapping("/api/product-details")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    public ProductDetailController(ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
    }

    @PostMapping
    public ProductDetailDTO createProduct(@RequestBody ProductDetailDTO productDetailDTO) {

        ProductDetail detail = new ProductDetail();
        detail.setSize(productDetailDTO.getSize());
        detail.setColor(productDetailDTO.getColor());
        detail.setStock(productDetailDTO.getStock());
        detail.setWarehouse(productDetailDTO.getWarehouse());
        detail.setProduct(productDetailDTO.getProduct());

        ProductDetail savedDetail = productDetailService.addDetail(detail);
        ProductDetailDTO responseDTO = new ProductDetailDTO();
        responseDTO.setSize(savedDetail.getSize());
        responseDTO.setColor(savedDetail.getColor());
        responseDTO.setStock(savedDetail.getStock());
        responseDTO.setWarehouse(savedDetail.getWarehouse());
        return responseDTO;
    }

    // UPDATE
    @PutMapping("/{id}")
    public ProductDetailDTO updateProduct(@PathVariable UUID id, @RequestBody ProductDetailDTO productDetailDTO) {

        ProductDetail detail = new ProductDetail();
        detail.setId(id);
        detail.setSize(productDetailDTO.getSize());
        detail.setColor(productDetailDTO.getColor());
        detail.setStock(productDetailDTO.getStock());
        detail.setWarehouse(productDetailDTO.getWarehouse());
        detail.setProduct(productDetailDTO.getProduct());

        ProductDetail updatedDetail = productDetailService.updateDetail(detail);
        ProductDetailDTO responseDTO = new ProductDetailDTO();
        responseDTO.setId(updatedDetail.getId());
        responseDTO.setSize(updatedDetail.getSize());
        responseDTO.setColor(updatedDetail.getColor());
        responseDTO.setStock(updatedDetail.getStock());
        responseDTO.setWarehouse(updatedDetail.getWarehouse());
        return responseDTO;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        productDetailService.delete(id);
    }
}
