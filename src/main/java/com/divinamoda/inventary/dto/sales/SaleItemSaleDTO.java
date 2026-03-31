package com.divinamoda.inventary.dto.sales;

import java.math.BigDecimal;
import java.util.UUID;

import com.divinamoda.inventary.dto.products.ProductDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleItemSaleDTO {

   // private Sale sale;
    private ProductDetailDTO productDetail;
    private UUID id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

}