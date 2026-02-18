package com.divinamoda.inventary.service;

import com.divinamoda.inventary.dto.sales.SaleDTO;
import com.divinamoda.inventary.entity.sales.Sale;

public interface SaleService {
    
    Sale saveSale(SaleDTO sale);
}
