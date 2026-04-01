package com.divinamoda.inventary.entity.sales;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sales")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String type; 
    // order / direct_sale

    @Column(nullable = false)
    private String status; 
    // pending / paid / delivered / cancelled

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amountPaid;

    @Column(nullable = false)
    private String paymentMethod;
    // cash / transfer / qr / card

    @Column(nullable = false)
    private Boolean active = true;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @Column(name = "sold_by", length = 100)
    private String soldBy;

    @Column(name = "delivered_by", length = 100)
    private String deliveredBy;

    @Column(name = "seller_profit", precision = 10, scale = 2)
    private BigDecimal sellerProfit = BigDecimal.ZERO;

    @Column(name = "delivery_profit", precision = 10, scale = 2)
    private BigDecimal deliveryProfit = BigDecimal.ZERO;

    @Column(name = "divina_profit", precision = 10, scale = 2)
    private BigDecimal divinaProfit = BigDecimal.ZERO;

    @Column(name = "sold_paid")
    private Boolean soldPaid = false;

    @Column(name = "delivery_paid")
    private Boolean deliveryPaid = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Column(name = "deleted_by", length = 100)
    private String deletedBy;

}
