package com.example.demo.Service;

import com.example.demo.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(Integer id);
    void updateProduct(Product product);
	List<Product> findAllProduct();
	void saveProduct(Product product);
}

