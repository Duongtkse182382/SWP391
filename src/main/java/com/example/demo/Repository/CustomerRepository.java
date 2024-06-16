package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	 Optional<Customer> findByPhone(String phone);
}
