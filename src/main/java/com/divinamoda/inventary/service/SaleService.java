package com.divinamoda.inventary.service;

import java.util.List;
import java.util.UUID;

import com.divinamoda.inventary.dto.sales.SaleDTO;
import com.divinamoda.inventary.dto.sales.SaleItemSaleDTO;
import com.divinamoda.inventary.entity.sales.Sale;

public interface SaleService {
    
    Sale saveSale(SaleDTO sale);
    List<Sale> listAllSales(String type);
    List<SaleItemSaleDTO> getSaleItemsBySaleId(UUID saleId);
    SaleDTO updateSaleDetail(UUID saleId, com.divinamoda.inventary.dto.sales.SaleDetailDTO detailDTO);
}
