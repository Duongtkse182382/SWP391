package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Material;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.MaterialRepository;
import com.example.demo.Repository.OrderDetailRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.TypeRepository;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.OrderService;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductOrderDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private TypeRepository typeRepository;

	@Override
	public Page<Order> getAllOrders(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public void saveProductFromOrder(OrderDTO orderDTO) {
		Order order = new Order();

		order.setTotal(orderDTO.getTotal());
		order.setQuantity(orderDTO.getProduct().size());
		order.setStaffID(1);
		order.setOrderstatusID(1);
		Double total = 0.0;
		for (int i = 0; i < orderDTO.getProduct().size(); i++) {
			total += orderDTO.getProduct().get(i).getMaterialPriceList().getSellPrice();
		}
		order.setTotal(total);
		order.setDate(new Date());

		int lpoitn = (int) (total * 0.1);
		String phone = orderDTO.getPhoneNumber();
		String name = orderDTO.getCustomerName();
		Customer cus = customerService.insertOrUpdateCustomer(phone, name, lpoitn);
		order.setCustomerID(cus.getCustomerID());
		
		orderRepository.save(order);

		for (int i = 0; i < orderDTO.getProduct().size(); i++) {
			OrderDetail orDetail = new OrderDetail();
			orDetail.setOrderID(order.getOrderID());
			orDetail.setProductID(orderDTO.getProduct().get(i).getProductID());
			orDetail.setTotal((orderDTO.getProduct().get(i).getMaterialPriceList().getSellPrice()));
			orderDetailRepository.save(orDetail);
		}

	}
}
