package com.example.demo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductOrderDTO {
	private int productId;
	private String productName;
	private String productCode;
	private int typeID;
	private String typeName;
	private String materialName;
	private float weight;
	private float sellPrice;	
	private int detailId;
	private int customerId;
	private int orderId;
    private int quantity;
    private String staffId;
    private Double total;
	
}
