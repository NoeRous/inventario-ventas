package com.divinamoda.inventary.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divinamoda.inventary.entity.sales.SaleItem;

public interface SaleItemRepository extends JpaRepository<SaleItem, UUID> {
    
}
