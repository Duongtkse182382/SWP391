package com.example.demo.Controller;

import com.example.demo.Entity.Order;
import com.example.demo.Entity.Product;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductOrderDTO;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
	private OrderService orderService;
	private ProductService productService;
	private OrderDTO orderDTOs;

	@Autowired
	public OrderController(OrderService orderService, ProductService productService) {
		this.orderService = orderService;
		this.productService = productService;
	}

	@GetMapping("/orders")
	public String showHistoryOrderList(Model model, @RequestParam(defaultValue = "0") int page) {
		Page<Order> orderPage = orderService.getAllOrders(PageRequest.of(page, 10));
		model.addAttribute("orders", orderPage);
		model.addAttribute("currentPage", orderPage.getNumber());
		model.addAttribute("totalPages", orderPage.getTotalPages());
		return "cashier/historyOrder";
	}

	@GetMapping("/orders/listOfOrder")
	public String showOrderList(Model model, @RequestParam(defaultValue = "0") int page) {
		Page<Order> orderPage = orderService.getAllOrders(PageRequest.of(page, 10));
		model.addAttribute("orders", orderPage);
		model.addAttribute("currentPage", orderPage.getNumber());
		model.addAttribute("totalPages", orderPage.getTotalPages());
		return "seller/listOfOrder";
	}

	private Set<Integer> addedProductIds = new HashSet<>();

	@GetMapping("/orders/new-sell-order")
	public String showNewSellOrder(@RequestParam(name = "productId", required = false) Integer productId, Model model) {
		if (orderDTOs == null) {
			orderDTOs = new OrderDTO();
		}
		if (orderDTOs.getProduct() == null) {
			orderDTOs.setProduct(new ArrayList<>());
		}

		if (productId != null && !addedProductIds.contains(productId)) {
			Product product = productService.findById(productId)
					.orElseThrow(() -> new RuntimeException("Product not found"));
			if (product != null) {
				orderDTOs.getProduct().add(product);
				addedProductIds.add(productId);
			}
		}

		List<Product> products = productService.findAllProduct();
		model.addAttribute("products", products);
		model.addAttribute("orderDto", orderDTOs); // Updated attribute name
		return "seller/newSellOrder";
	}

	@PostMapping("/orders/new-sell-order/save")
	public String saveNewSellOrder(@Valid @ModelAttribute("orderDto") OrderDTO orderDTO, BindingResult result,
			Model model) {
		orderDTO.setStaffID(2);
		orderDTOs.setPhoneNumber(orderDTO.getPhoneNumber());
		orderDTOs.setCustomerName(orderDTO.getCustomerName());
		orderService.saveProductFromOrder(orderDTOs);
		orderDTOs = null;
		return "seller/newSellOrder"; // Redirect to another page after saving

	}
	
	@GetMapping("/orders/SellOrderDetail")
    public String saleOrderDetail() {
    	return "seller/sellOrderDetail";
    }
   
    @GetMapping("/orders/PurchaseOrderDetail")
    public String purchaseOrderDetail() {
    	return "seller/purchaseOrderDetail";
    
}
    @GetMapping("counter")
    public String counterList() {
    	return "manager/counterList";
    }
    @GetMapping("counter/counterDetail")
    public String counterDetail() {
    	return "manager/counterDetail";
    }
}
