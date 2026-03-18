package com.divinamoda.inventary.service;

import java.util.List;

import com.divinamoda.inventary.dto.sales.SaleDTO;
import com.divinamoda.inventary.entity.sales.Sale;

public interface SaleService {
    
    Sale saveSale(SaleDTO sale);
    List<Sale> listAllSales(String type);
}
