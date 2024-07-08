package com.example.demo.Repository;

import com.example.demo.Entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
	@Query("SELECT COUNT(p) FROM Product p WHERE p.productCode = :productCode AND p.id != :productId")
	int countByProductCodeAndIdNot(@Param("productCode") String productCode, @Param("productId") Integer productId);

    boolean existsByProductCode(String productCode);
	
}
