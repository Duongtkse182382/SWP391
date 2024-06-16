package com.example.demo.Service;
import com.example.demo.Entity.Customer;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
	  Page<Customer> getAllCustomers(Pageable pageable);
	    Optional<Customer> getCusByPhoneNumber(String phoneNumber);
	    Customer insertCustomer(Customer customer);
	    Customer updateCustomer(Customer customer);
	    Customer insertOrUpdateCustomer(String phone, String name, int loyalPoint);
}
