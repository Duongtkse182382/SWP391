package com.example.demo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderDTO {
	
    
    private Integer typeGold;
    private String materialName;
    private int staffId;
    private float weight;
    private float diamondPrice;
    private float goldPrice;

    private String orderDate;
    private String staffName;
    private String customerName;
    private String phoneNumber;
}
