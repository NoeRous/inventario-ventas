package com.divinamoda.inventary.entity.products;

import java.util.UUID;

import com.divinamoda.inventary.entity.products.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product_details") 
@Data
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String warehouse;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
    
}
