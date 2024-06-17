package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Entity.Gem;
import com.example.demo.Entity.Material;
import com.example.demo.Repository.MaterialRepository;
import com.example.demo.Service.GemService;
import com.example.demo.Service.MaterialService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.PurchaseOrderService;
import com.example.demo.dto.PurchaseOrderDTO;

@Controller
public class PurchaseOrderController {
	private PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
	private MaterialService materialService;
	private GemService gemService;
	private PurchaseOrderService purchaseOrderService;

	public PurchaseOrderController(MaterialService materialService, GemService gemService,PurchaseOrderService purchaseOrderService) {
		this.materialService = materialService;
		this.gemService = gemService;
		this.purchaseOrderService=purchaseOrderService;
	}

	@GetMapping("/orders/NewPurchaseOrder")
	public String editPurchaseOrder(@RequestParam(name = "dimondCode", required = false) String dimondCode,
			@RequestParam(name = "typeGold", required = false) Integer typeGold,
			@RequestParam(name = "weight", required = false) Float weight, Model model) 
	{
		if (!"".equals(dimondCode)) {
			Gem gem = gemService.getGemByGemCode(dimondCode);
			if (gem != null) {
				purchaseOrderDTO.setDiamondCode(dimondCode);
				purchaseOrderDTO.setOrigin(gem.getOrigin());
				purchaseOrderDTO.setCarat(gem.getCarat());
				purchaseOrderDTO.setClarity(gem.getClarity());
				purchaseOrderDTO.setColor(gem.getColor());
				purchaseOrderDTO.setCut(gem.getCut());
				if (!gem.getGemPriceLists().isEmpty()) {
					purchaseOrderDTO.setDiamondPrice(
							gem.getGemPriceLists().get(gem.getGemPriceLists().size() - 1).getBuyPrice());
				}
			}else {
	            // Reset specific fields if gem is null
				purchaseOrderDTO.setDiamondCode(dimondCode);
				purchaseOrderDTO.setOrigin(null); // Reset origin
				purchaseOrderDTO.setCarat(0); // Reset carat
				purchaseOrderDTO.setClarity(null); // Reset clarity
				purchaseOrderDTO.setColor(null); // Reset color
				purchaseOrderDTO.setCut(null); // Reset cut
				purchaseOrderDTO.setDiamondPrice(0); // Reset diamond price
	        }
		}
		model.addAttribute("purchaseOrderDTO", purchaseOrderDTO);
		List<Material> materials = new ArrayList<>();
		materials = materialService.getAllMaterials();
		model.addAttribute("materials", materials);		
		return "seller/NewPurchaseOrder";

	}

	
	@GetMapping("/orders/updatePrice")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updatePrice(@RequestParam int typeGold, @RequestParam float weight) {
		Optional<Material> material = materialService.getMaterialById(typeGold);
		
	    float goldPrice = weight * (material.get().getMaterialPriceLists().get(material.get().getMaterialPriceLists().size()-1).getBuyPrice()); 
	    purchaseOrderDTO.setWeight(weight);
	    purchaseOrderDTO.setGoldPrice(goldPrice);
	    Map<String, Object> response = new HashMap<>();
	    response.put("goldPrice", goldPrice);
	    return ResponseEntity.ok(response);
	}
	
	

	@PostMapping("/orders/NewPurchaseOrder/save")
	public String savePurchaseOrder(@ModelAttribute PurchaseOrderDTO purchaseOrderDTO, Model model) {
		
		purchaseOrderService.savePurchaseOrder(purchaseOrderDTO);
		purchaseOrderDTO=new PurchaseOrderDTO();
		
		List<Material> materials = new ArrayList<>();
		materials = materialService.getAllMaterials();
		model.addAttribute("materials", materials);		
		model.addAttribute("purchaseOrder", purchaseOrderDTO);
		return "seller/NewPurchaseOrder";
	}
}
