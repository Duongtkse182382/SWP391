package com.example.demo.dto;

import java.util.List;

import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderGoldDto {

	private Order order;
    private List<OrderDetail> orderDetails;
}
