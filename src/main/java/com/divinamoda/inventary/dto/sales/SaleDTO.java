package com.divinamoda.inventary.dto.sales;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class SaleDTO {

   // private LocalDateTime date;
    private String type; // order / direct_sale
    private String status; // pending / paid / delivered / cancelled
    private UUID customerId; 
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal total;
    private BigDecimal amountPaid;
    private String paymentMethod; // cash / transfer / qr / card
    private SaleItemDTO[] saleItems;


     // Detalle de venta
    private String observation;
    private String soldBy;
    private BigDecimal sellerProfit;
    private Boolean soldPaid;
    private String deliveredBy;
    private BigDecimal deliveryProfit;
    private Boolean deliveryPaid;
    
}
