package com.example.demo.Repository;

import com.example.demo.Entity.Order;
import com.example.demo.dto.ProductOrderDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	void save(ProductOrderDTO productOrder);
	

}