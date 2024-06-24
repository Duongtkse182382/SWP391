package com.example.demo.Service;

import com.example.demo.Entity.Order;
import com.example.demo.dto.OrderDTO;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<Order> getAllOrders(Pageable pageable);
    List<Order> getAllOrders();
    Order saveOrder(Order order);
	void saveProductFromOrder(OrderDTO orderDTO);
	Optional<Order> findOrderById(Integer id);
	void updateOrder(Order order);
	
	
}
