package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Material;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.MaterialRepository;
import com.example.demo.Repository.OrderDetailRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.TypeRepository;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.OrderService;
import com.example.demo.dto.OrderDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	@Autowired
	private CustomerRepository customerRepository;

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
//		order.setStaffID(1);
		order.setStaffID(orderDTO.getStaffID());
		order.setOrderstatusID(1);
		Double total = 0.0;
		for (int i = 0; i < orderDTO.getProduct().size(); i++) {
			total += ((orderDTO.getProduct().get(i).getMaterialPriceList().getSellPrice()*(orderDTO.getProduct().get(i).getWeight()/3.75) 
					+ orderDTO.getProduct().get(i).getGemPriceList().getSellPrice())*orderDTO.getProduct().get(i).getPriceRate());	 
		}
		order.setTotal(total);
		order.setDate(new Date());
		System.out.print("abc");
System.out.print(orderDTO.getStaffID());
		int lpoitn = 0;
		String phone = orderDTO.getPhoneNumber();
		String name = orderDTO.getCustomerName();
		Customer cus = customerService.insertOrUpdateCustomer(phone, name, lpoitn);
		order.setCustomerID(cus.getCustomerID());
		
		orderRepository.save(order);

		for (int i = 0; i < orderDTO.getProduct().size(); i++) {
			OrderDetail orDetail = new OrderDetail();
			orDetail.setOrderID(order.getOrderID());
			orDetail.setProductID(orderDTO.getProduct().get(i).getProductID());
			orDetail.setTotal((float) ((orderDTO.getProduct().get(i).getMaterialPriceList().getSellPrice()*(orderDTO.getProduct().get(i).getWeight()/3.75) 
					+ orderDTO.getProduct().get(i).getGemPriceList().getSellPrice())*orderDTO.getProduct().get(i).getPriceRate()));
			orderDetailRepository.save(orDetail);
		}
	} 
	@Override
	public Optional<Order> findOrderById(Integer id){
		return orderRepository.findById(id);
	}
	
	 @Override
	    public void updateOrder(Order order) {
	        orderRepository.save(order);
	    }
	 public void updateLoyaltyPoints(Order order) {
		    Customer customer = order.getCustomer();
		    if (customer != null) {
		        int pointsEarned = (int) (order.getTotal() * 0.01); 
		        customer.setLoyalPoint(customer.getLoyalPoint() + pointsEarned);
		        customerRepository.save(customer);
		    }
		}


}
