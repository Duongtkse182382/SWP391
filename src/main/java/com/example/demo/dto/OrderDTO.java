package com.example.demo.dto;


import java.util.List;

import com.example.demo.Entity.Product;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String phoneNumber;
    private int staffID;
    private String customerName;
    private int orderID;
    private int productID;
    private int promotionID;
    private int quantity;
    private String name;
    private Double total;
    private List<Product> Product;
	

}
