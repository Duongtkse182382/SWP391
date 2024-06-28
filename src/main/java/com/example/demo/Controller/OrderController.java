package com.example.demo.Controller;

import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Promotion;
import com.example.demo.Entity.Staff;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.StaffRepository;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.PromotionService;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductOrderDTO;
import com.example.demo.dto.PurchaseOrderGoldDto;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Date;
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
	 @Autowired
	  private OrderRepository orderRepository;
	 private PromotionService promotionService;
   private OrderService orderService;
	private ProductService productService;
	private OrderDTO orderDTOs;
	private PurchaseOrderGoldDto purchaseOrderGoldDto;
	private PurchaseOrderGoldDto purchaseSaveGoldDto;
	private List<OrderDetail> orDetails;
	private List<OrderDetail> purchaseDetails;
	private List<Product> lProduct;
	private int oldId=0;
	public OrderController(OrderService orderService, ProductService productService,PromotionService promotionService) {
		this.orderService = orderService;
		this.productService = productService;
		 this.promotionService = promotionService;
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
		Page<Order> orderPage = orderRepository.findByOrderCodeStartingWithS(PageRequest.of(page, 10));
		model.addAttribute("orders", orderPage);
		model.addAttribute("currentPage", orderPage.getNumber());
		model.addAttribute("totalPages", orderPage.getTotalPages());
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
         Staff staff = staffRepository.findByEmail(email);
         model.addAttribute("staff", staff);
		return "seller/listOfOrder";
	}
	    @GetMapping("/orders/listOfPurchaseOrder")
	    public String showPurchaseOrderList(Model model, @RequestParam(defaultValue = "0") int page) {
	        Page<Order> orderPage = orderRepository.findByOrderCodeStartingWithP(PageRequest.of(page, 10));
	        model.addAttribute("orders", orderPage);
	        model.addAttribute("currentPage", orderPage.getNumber());
	        model.addAttribute("totalPages", orderPage.getTotalPages());
	        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	        Staff staff = staffRepository.findByEmail(email);
	        model.addAttribute("staff", staff);
	        return "seller/listOfPurchaseOrder";
	    }

	private Set<Integer> addedProductIds = new HashSet<>();

	@GetMapping("/orders/new-sell-order")
	public String showNewSellOrder(@RequestParam(required = false) Integer productId,@RequestParam(required = false) Integer removeId, Model model) {
		if (orderDTOs == null) {
			orderDTOs = new OrderDTO();
			lProduct=new ArrayList<>();
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
		
		if (removeId != null ) {
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
			Model model,HttpSession session) {
	    Staff loggedInStaff = (Staff) session.getAttribute("staff");

	    if (loggedInStaff != null) {
	        System.out.println("Currently logged-in staff ID: " + loggedInStaff.getStaffID());
	        System.out.println("Currently logged-in staff name: " + loggedInStaff.getFullName());
	        orderDTOs.setStaffID(loggedInStaff.getStaffID());
	    } else {
	        System.out.println("No staff information found in session.");
	    }
			
		orderDTOs.setPhoneNumber(orderDTO.getPhoneNumber());
		orderDTOs.setCustomerName(orderDTO.getCustomerName());
		orderService.saveProductFromOrder(orderDTOs);
		orderDTOs = new OrderDTO();
		addedProductIds = new HashSet<>();
		lProduct.clear();
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
         Staff staff = staffRepository.findByEmail(email);
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
		@GetMapping("/dashboard")
	    public String showDashboard(Model model) {
		       String email = SecurityContextHolder.getContext().getAuthentication().getName();
		        Staff staff = staffRepository.findByEmail(email);
		        model.addAttribute("staff", staff);
		        Double totalSum = orderRepository.getTotalSum();
		        Double totalSumWithS = orderRepository.getTotalSumWithOrderCodeStartingWithS();
		        Double totalSumWithP = orderRepository.getTotalSumWithOrderCodeStartingWithP();
		        List<Date> datesWithS = orderRepository.getDistinctDatesWithOrderCodeStartingWithS();
		        List<Date> datesWithP = orderRepository.getDistinctDatesWithOrderCodeStartingWithP();
		        List<Object[]> totalSumByDateS = orderRepository.getTotalSumByDateForOrderCodeStartingWithS();
		        List<Object[]> totalSumByDateP = orderRepository.getTotalSumByDateForOrderCodeStartingWithP();

		        model.addAttribute("totalSumByDateS", totalSumByDateS);
		        model.addAttribute("totalSumByDateP",  totalSumByDateP);
		        model.addAttribute("datesWithS",  datesWithS);
				model.addAttribute("datesWithP", datesWithP);
		        model.addAttribute("totalSum", totalSum);
		        model.addAttribute("totalSumWithS", totalSumWithS);
		        model.addAttribute("totalSumWithP", totalSumWithP);
		        return "manager/dashboard";
	    }

		@GetMapping("/seller/products/bill-of-sell/{orderID}")
		public String showBillOfSellById(@PathVariable Integer orderID, Model model) {
			Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
			 String email = SecurityContextHolder.getContext().getAuthentication().getName();
		       Staff staff = staffRepository.findByEmail(email);     
		       List<Promotion> promotions = promotionService.findAll();
		        model.addAttribute("promotions", promotions);
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
	       List<Promotion> promotions = promotionService.findAll();
	        model.addAttribute("promotions", promotions);
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