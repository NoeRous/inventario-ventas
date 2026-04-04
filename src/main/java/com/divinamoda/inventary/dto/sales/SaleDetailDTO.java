package com.divinamoda.inventary.dto.sales;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SaleDetailDTO {

    private String observation;

    private String soldBy;

    private BigDecimal sellerProfit;

    private Boolean soldPaid;

    private String deliveredBy;

    private BigDecimal deliveryProfit;

    private Boolean deliveryPaid;
}