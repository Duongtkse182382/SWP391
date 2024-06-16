package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Customer;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Service.CustomerService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	 @Autowired
	    private CustomerRepository customerRepository;

	    @Override
	    public Page<Customer> getAllCustomers(Pageable pageable) {
	        return customerRepository.findAll(pageable);
	    }

	    @Override
	    public Optional<Customer> getCusByPhoneNumber(String phoneNumber) {
	        return customerRepository.findByPhone(phoneNumber);
	    }

	    @Override
	    public Customer insertCustomer(Customer customer) {
	        return customerRepository.save(customer);
	    }

	    @Override
	    public Customer updateCustomer(Customer customer) {
	        if (customerRepository.existsById(customer.getCustomerID())) {
	            return customerRepository.save(customer);
	        } else {
	            throw new IllegalArgumentException("Customer with ID " + customer.getCustomerID() + " does not exist.");
	        }
	    }
	    
	    @Override
	    public Customer insertOrUpdateCustomer(String phone, String name, int loyalPoint) {
	        Optional<Customer> optionalCustomer = customerRepository.findByPhone(phone);
	        Customer customer;
	        if (optionalCustomer.isPresent()) {
	            customer = optionalCustomer.get();
	            customer.setCustomerName(name);
	            customer.setLoyalPoint(customer.getLoyalPoint() + loyalPoint); // Cộng thêm loyalPoint mới vào loyalPoint cũ
	        } else {
	            customer = new Customer();
	            customer.setPhone(phone);
	            customer.setCustomerName(name);
	            customer.setLoyalPoint(loyalPoint); // Thiết lập loyalPoint mới nếu khách hàng chưa tồn tại
	        }
	        return customerRepository.save(customer);
	    }
}
