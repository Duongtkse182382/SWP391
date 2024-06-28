package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Staff;
import com.example.demo.Repository.StaffRepository;
import com.example.demo.Service.CustomerService;


@Controller
public class CustomerController {
	 private final CustomerService customerService;
	  @Autowired
	    private StaffRepository staffRepository;

	    public CustomerController(CustomerService customerService) {
	        this.customerService = customerService;
	    }
	    @GetMapping("/customers")
	    public String showCustomerList(Model model, @RequestParam(defaultValue = "0") int page) {
	        Page<Customer> customerPage = customerService.getAllCustomers(PageRequest.of(page, 10));
	        model.addAttribute("customers", customerPage.getContent());
	        model.addAttribute("currentPage", customerPage.getNumber());
	        model.addAttribute("totalPages", customerPage.getTotalPages());
	        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	           Staff staff = staffRepository.findByEmail(email);
	           model.addAttribute("staff", staff);
	        return "cashier/listCustomer";
	    }

}
