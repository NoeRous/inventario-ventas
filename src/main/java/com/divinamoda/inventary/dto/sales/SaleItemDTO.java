package com.divinamoda.inventary.dto.sales;

import java.math.BigDecimal;
import java.util.UUID;

import com.divinamoda.inventary.entity.products.ProductDetail;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class SaleItemDTO {

    private UUID productDetailId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
