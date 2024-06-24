package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Gem;
import com.example.demo.Entity.GemPriceList;
import com.example.demo.Entity.Material;
import com.example.demo.Entity.MaterialPriceList;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.PurchaseDetail;
import com.example.demo.Repository.OrderDetailRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.GemService;
import com.example.demo.Service.MaterialPriceListService;
import com.example.demo.Service.MaterialService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.PurchaseOrderService;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.PurchaseOrderDTO;
import com.example.demo.dto.PurchaseOrderGoldDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private GemService gemService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private MaterialPriceListService materialPriceListService;

	@Override
	public void savePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
		Order order = new Order();
		int quantity = 0;
		double total = (double) (purchaseOrderDTO.getDiamondPrice() + purchaseOrderDTO.getGoldPrice());
		order.setTotal(total);

		order.setQuantity(quantity);
		order.setStaffID(purchaseOrderDTO.getStaffId());
		order.setOrderstatusID(2);
		order.setQuantity(1);
		order.setDate(new Date());
		order.setType(true);
		int lpoint = 0;
		String phone = purchaseOrderDTO.getPhoneNumber();
		String name = purchaseOrderDTO.getCustomerName();
		Customer cus = customerService.insertOrUpdateCustomer(phone, name, lpoint);
		order.setCustomerID(cus.getCustomerID());
		
	
		// New product material
		Product product = new Product();
		List<MaterialPriceList> materialPrice = materialPriceListService
				.getMaterialPriceListById(purchaseOrderDTO.getTypeGold().intValue());
		MaterialPriceList maPriceList = materialPrice.get(0);
		product.setCategoryID(5);
		product.setTypeID(2);
		product.setGemPriceListID(1);
		product.setOrderType("Purchare");
		product.setActive(true);
		product.setMaterialPriceListID(maPriceList.getMaterialPriceListID());
		product.setCategory(null);
		product.setWeight(purchaseOrderDTO.getWeight());
		product.setProductName(purchaseOrderDTO.getMaterialName());
		product.setProductCode("Materail");
		productRepository.save(product);

		orderRepository.save(order);
		
		OrderDetail orDetail = new OrderDetail();
		orDetail.setOrderID(order.getOrderID());
		orDetail.setProductID(product.getProductID());
		orDetail.setTotal(purchaseOrderDTO.getGoldPrice());
		orderDetailRepository.save(orDetail);
	}

	@Override
	public void savePurGold(PurchaseOrderGoldDto purchaseOrderGoldDto) {
		Order order = new Order();

		// In thông tin của purchaseOrderGoldDto ra console
		System.out.println("Order ID: " + purchaseOrderGoldDto.getOrder().getOrderID());
		System.out.println("Order Date: " + purchaseOrderGoldDto.getOrder().getDate());
		System.out.println("Staff Name: " + purchaseOrderGoldDto.getOrder().getStaff().getFullName());
		System.out.println("Customer Name: " + purchaseOrderGoldDto.getOrder().getCustomer().getCustomerName());
		System.out.println("Phone Number: " + purchaseOrderGoldDto.getOrder().getCustomer().getPhone());
		List<OrderDetail> orderDetails = purchaseOrderGoldDto.getOrderDetails();
		for (OrderDetail orderDetail : orderDetails) {
			System.out.println("Product Code: " + orderDetail.getProduct().getProductCode());
			System.out.println("Product Name: " + orderDetail.getProduct().getProductName());
			System.out.println("Type: " + orderDetail.getProduct().getType().getTypeName());
			System.out.println("Material: " + orderDetail.getProduct().getGemPriceList().getGem().getGemName());
			System.out.println("Weight/Carat: " + orderDetail.getProduct().getGemPriceList().getGem().getCarat());
			System.out.println("Total: " + orderDetail.getTotal());
			System.out.println("------");
		}
		List<OrderDetail> details = purchaseOrderGoldDto.getOrderDetails();
		Double total = 0.0;
		for (OrderDetail item : details) {
			total += item.getProduct().getMaterialPriceList().getBuyPrice() * item.getProduct().getWeight()
					+ item.getProduct().getGemPriceList().getBuyPrice();
		}
		order.setTotal(total);
		order.setDate(new Date());
		order.setStaffID(purchaseOrderGoldDto.getOrder().getStaffID());
		String phone = purchaseOrderGoldDto.getOrder().getCustomer().getPhone();
		String name = purchaseOrderGoldDto.getOrder().getCustomer().getCustomerName();
		Customer cus = customerService.insertOrUpdateCustomer(phone, name, 0);
		order.setCustomerID(cus.getCustomerID());
		order.setType(true);
		order.setOrderstatusID(2);
		order.setQuantity(details.size());
		orderRepository.save(order);

		for (OrderDetail item : details) {
			OrderDetail orDetail = new OrderDetail();
			orDetail.setOrderID(order.getOrderID());
			orDetail.setProductID(item.getProduct().getProductID());
			orDetail.setTotal((item.getProduct().getMaterialPriceList().getBuyPrice() * item.getProduct().getWeight()
					+ item.getProduct().getGemPriceList().getBuyPrice()));
			orderDetailRepository.save(orDetail);
		}
	}

}
