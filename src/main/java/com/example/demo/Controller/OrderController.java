package com.example.demo.Controller;

import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Staff;
import com.example.demo.Repository.StaffRepository;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductOrderDTO;
import com.example.demo.dto.PurchaseOrderGoldDto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
	@Autowired
	private StaffRepository staffRepository;
	private OrderService orderService;
	private ProductService productService;
	private OrderDTO orderDTOs;
	private PurchaseOrderGoldDto purchaseOrderGoldDto;
	private PurchaseOrderGoldDto purchaseSaveGoldDto;
	private List<OrderDetail> orDetails;
	private List<OrderDetail> purchaseDetails;
	private List<Product> lProduct;
	private int oldId = 0;

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
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		return "cashier/historyOrder";
	}

	@GetMapping("/orders/listOfOrder")
	public String showOrderList(Model model, @RequestParam(defaultValue = "0") int page) {
		Page<Order> orderPage = orderService.getAllOrders(PageRequest.of(page, 10));
		model.addAttribute("orders", orderPage);
		model.addAttribute("currentPage", orderPage.getNumber());
		model.addAttribute("totalPages", orderPage.getTotalPages());
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		return "seller/listOfOrder";
	}

	private Set<Integer> addedProductIds = new HashSet<>();

	@GetMapping("/orders/new-sell-order")
	public String showNewSellOrder(@RequestParam(required = false) Integer productId,
			@RequestParam(required = false) Integer removeId, Model model) {
		if (orderDTOs == null) {
			orderDTOs = new OrderDTO();
			lProduct = new ArrayList<>();
		}
		if (orderDTOs.getProduct() == null) {
			orderDTOs.setProduct(new ArrayList<>());
		}

		if (productId != null && !addedProductIds.contains(productId)) {
			Product product = productService.findById(productId)
					.orElseThrow(() -> new RuntimeException("Product not found"));
			if (product != null) {
				lProduct.add(product);
				addedProductIds.add(productId);
			}
		}

		if (removeId != null) {
			Iterator<Product> item = lProduct.iterator();
			while (item.hasNext()) {
				Product product = item.next();
				if (product.getProductID() == removeId) {
					item.remove(); // Remove the OrderDetail that matches detailID
				}
			}
		}
		orderDTOs.setProduct(lProduct);
		List<Product> products = productService.findAllProduct();
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		model.addAttribute("products", products);
		model.addAttribute("orderDto", orderDTOs); // Updated attribute name
		return "seller/newSellOrder";
	}

	@PostMapping("/orders/new-sell-order/save")
	public String saveNewSellOrder(@Valid @ModelAttribute("orderDto") OrderDTO orderDTO, BindingResult result,
			Model model, HttpSession session) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		orderDTOs.setStaffID(staff.getStaffID());
		orderDTOs.setPhoneNumber(orderDTO.getPhoneNumber());
		orderDTOs.setCustomerName(orderDTO.getCustomerName());
		orderService.saveProductFromOrder(orderDTOs);
		orderDTOs = new OrderDTO();
		addedProductIds = new HashSet<>();
		lProduct.clear();

		model.addAttribute("staff", staff);
		return "seller/newSellOrder";

	}

	@GetMapping("/orders/SellOrderDetail/{orderID}")
	public String saleOrderDetail(@PathVariable Integer orderID, Model model) {
		Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		model.addAttribute("order", order);

		return "seller/sellOrderDetail";
	}

	@PostMapping("/orders/SellOrderDetail/{orderID}/complete")
	public String handleCompleteOrderDetailAction(@PathVariable Integer orderID, Model model) {
		Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
		order.setOrderstatusID(2);
		orderService.updateOrder(order);
		model.addAttribute("order", order);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		return "seller/sellOrderDetail";
	}

	@GetMapping("/orders/PurchaseOrderDetail/{orderID}")
	public String purchaseOrderDetail(@PathVariable Integer orderID, Model model) {
		Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
		model.addAttribute("order", order);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		return "seller/purchaseOrderDetail";
	}

	@PostMapping("/orders/PurchaseOrderDetail/{orderID}/save")
	public String handleSavePurchaseOrderDetailAction(@PathVariable Integer orderID, Model model) {
		Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
		order.setOrderstatusID(2);
		orderService.updateOrder(order);
		model.addAttribute("order", order);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		return "seller/purchaseOrderDetail";
	}

	@GetMapping("/counter")
	public String counterList(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		return "manager/counterList";
	}

	@GetMapping("/counter/counterDetail")
	public String counterDetail(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		return "manager/counterDetail";
	}

	@GetMapping("/seller/products/bill-of-sell/{orderID}")
	public String showBillOfSellById(@PathVariable Integer orderID, Model model) {
		Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		model.addAttribute("order", order);
		return "cashier/BillofSell";
	}

	@PostMapping("/seller/products/bill-of-sell/{orderID}/print")
	public String handlePrintAction(@PathVariable Integer orderID, @RequestParam Double totalAmount, Model model) {
		Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
		order.setOrderstatusID(3);
		order.setTotal(totalAmount);
		orderService.updateOrder(order);
		model.addAttribute("order", order);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		return "cashier/BillofSell";
	}

	@PostMapping("/seller/products/bill-of-sell/{orderID}/cancel")
	public String handleCancelAction(@PathVariable Integer orderID, Model model) {
		Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
		order.setOrderstatusID(4);
		orderService.updateOrder(order);
		model.addAttribute("order", order);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		return "cashier/BillofSell";
	}

	@GetMapping("/seller/products/bill-of-buy/{orderID}")
	public String showBillOfBuy(@PathVariable Integer orderID, Model model) {
		Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		model.addAttribute("order", order);

		return "cashier/BillofBuy";
	}

	@PostMapping("/seller/products/bill-of-buy/{orderID}/print")
	public String handlePrintBillOfBuyAction(@PathVariable Integer orderID, @RequestParam Double totalAmount,
			Model model) {
		Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
		order.setOrderstatusID(2);
		order.setTotal(totalAmount);
		orderService.updateOrder(order);
		model.addAttribute("order", order);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		return "cashier/BillofBuy";
	}

	@PostMapping("/seller/products/bill-of-buy/{orderID}/cancel")
	public String handleCancelBillOfSellAction(@PathVariable Integer orderID, Model model) {
		Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
		order.setOrderstatusID(1);
		orderService.updateOrder(order);
		model.addAttribute("order", order);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Staff staff = staffRepository.findByEmail(email);
		model.addAttribute("staff", staff);
		return "cashier/BillofBuy";
	}

}