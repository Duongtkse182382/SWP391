package com.example.demo.Service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.PurchaseOrderDTO;
import com.example.demo.dto.PurchaseOrderGoldDto;

public interface PurchaseOrderService {
	void savePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);
	void savePurGold(PurchaseOrderGoldDto purchaseOrderGoldDto);
}
