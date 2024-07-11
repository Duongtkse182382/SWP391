package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Entity.Gem;
import com.example.demo.Entity.Material;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Staff;
import com.example.demo.Repository.MaterialRepository;
import com.example.demo.Repository.StaffRepository;
import com.example.demo.Service.GemService;
import com.example.demo.Service.MaterialService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.PurchaseOrderService;
import com.example.demo.dto.PurchaseOrderDTO;
import com.example.demo.dto.PurchaseOrderGoldDto;

@Controller
public class PurchaseOrderController {
	private PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
	private MaterialService materialService;
	private OrderService orderService;
	private GemService gemService;
	private PurchaseOrderService purchaseOrderService;
	private PurchaseOrderGoldDto purchaseOrderGoldDto;
	private PurchaseOrderGoldDto purchaseSaveGoldDto;
	private List<OrderDetail> orDetails;
	private List<OrderDetail> purchaseDetails;
	private int oldId=0;
	   @Autowired
	    private StaffRepository staffRepository;
	public PurchaseOrderController(OrderService orderService,MaterialService materialService, GemService gemService,PurchaseOrderService purchaseOrderService) {
		this.materialService = materialService;
		this.gemService = gemService;
		this.purchaseOrderService=purchaseOrderService;
		this.orderService= orderService;
	}

	@GetMapping("/orders/NewPurchaseOrder")
	public String editPurchaseOrder(
			@RequestParam(required = false) Integer typeGold,
			@RequestParam(required = false) Float weight, Model model) 
	{
	
		model.addAttribute("purchaseOrderDTO", purchaseOrderDTO);
		List<Material> materials = new ArrayList<>();
		materials = materialService.getAllMaterials();
		model.addAttribute("materials", materials);		
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
		return "seller/NewPurchaseOrder";

	}

	
	@GetMapping("/orders/updatePrice")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updatePrice(@RequestParam int typeGold, @RequestParam float weight,Model model) {
		Optional<Material> material = materialService.getMaterialById(typeGold);
		
	    float goldPrice = (float) ((weight/3.75) * (material.get().getMaterialPriceLists().get(material.get().getMaterialPriceLists().size()-1).getBuyPrice())); 
	    purchaseOrderDTO.setWeight(weight);
	    purchaseOrderDTO.setGoldPrice(goldPrice);
	    Map<String, Object> response = new HashMap<>();
	    response.put("goldPrice", goldPrice);
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
	    return ResponseEntity.ok(response);
	}
	
	

	@PostMapping("/orders/NewPurchaseOrder/save")
	public String savePurchaseOrder(@ModelAttribute PurchaseOrderDTO purchaseOrderDTO, Model model,HttpSession session) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
	        purchaseOrderDTO.setStaffId(staff.getStaffID());
	   
		
		purchaseOrderService.savePurchaseOrder(purchaseOrderDTO);
		purchaseOrderDTO=new PurchaseOrderDTO();
		
		List<Material> materials = new ArrayList<>();
		materials = materialService.getAllMaterials();
		
	       model.addAttribute("staff", staff);
		model.addAttribute("materials", materials);		
		model.addAttribute("purchaseOrder", purchaseOrderDTO);
		return "seller/NewPurchaseOrder";
	}
	
	@GetMapping("/orders/sell-gold-order/{orderID}")
	public String sellGoldOrder(@PathVariable Integer orderID, @RequestParam(required = false) Integer detailID,
			Model model) {
		
		if (purchaseOrderGoldDto == null || oldId!=orderID) 
		{
			purchaseOrderGoldDto = new PurchaseOrderGoldDto();
			orDetails = new ArrayList<>();
			purchaseDetails = new ArrayList<>();
			Order order = orderService.findOrderById(orderID).orElseThrow(() -> new RuntimeException("Order not found"));
			purchaseOrderGoldDto.setOrder(order);
			orDetails = order.getOrderDetails();
			oldId=orderID;

		}	
		if (detailID != null ) {
			boolean added = false;
			Iterator<OrderDetail> iterator = orDetails.iterator();
			while (iterator.hasNext()) {
				OrderDetail orderDetail = iterator.next();
				if (orderDetail.getDetailID() == detailID) {
					added = true;
					purchaseDetails.add(orderDetail);
					iterator.remove(); 
				}
			}
			if (!added) {
				Iterator<OrderDetail> item = purchaseDetails.iterator();
				while (item.hasNext()) {
					OrderDetail orderDetail = item.next();
					if (orderDetail.getDetailID() == detailID) {
						orDetails.add(orderDetail);
						item.remove();
					}
				}
			}

			for(OrderDetail item:purchaseDetails) 
			{
				float buyPrice=item.getProduct().getMaterialPriceList().getBuyPrice()*item.getProduct().getWeight() + item.getProduct().getGemPriceList().getBuyPrice();;
				item.setTotal(buyPrice);
			}
			
			for(OrderDetail item:orDetails) 
			{
				float buyPrice=item.getProduct().getMaterialPriceList().getSellPrice()*item.getProduct().getWeight() + item.getProduct().getGemPriceList().getSellPrice();;
				item.setTotal(buyPrice);
			}
			
			purchaseOrderGoldDto.getOrder().setOrderDetails(orDetails);
			purchaseOrderGoldDto.setOrderDetails(purchaseDetails);
		}
		purchaseSaveGoldDto=purchaseOrderGoldDto;
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
		model.addAttribute("purchaseOrderGoldDto", purchaseOrderGoldDto);
		return "seller/SellGoldOrder"; 
	}
	
	@PostMapping("/orders/new-pur-gold/save")
	public String saveNewPurchaseGold(@Valid @ModelAttribute("purchaseOrderGoldDto") PurchaseOrderGoldDto purchaseOrderGoldDto, BindingResult result,
			Model model) {	
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
		
		purchaseOrderGoldDto=purchaseSaveGoldDto;
		purchaseOrderService.savePurGold(purchaseOrderGoldDto);
		return "redirect:/orders/listOfOrder";

	}
}
