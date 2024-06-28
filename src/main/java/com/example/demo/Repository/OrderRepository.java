package com.example.demo.Repository;

import com.example.demo.Entity.Order;
import com.example.demo.dto.ProductOrderDTO;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	void save(ProductOrderDTO productOrder);
	
	@Query("SELECT o FROM Order o WHERE o.orderCode LIKE 'S%'")
	Page<Order> findByOrderCodeStartingWithS(Pageable pageable);
	
	@Query("SELECT o FROM Order o WHERE o.orderCode LIKE 'P%'")
	Page<Order> findByOrderCodeStartingWithP(Pageable pageable);
 
    @Query("SELECT SUM(o.total) FROM Order o")
    Double getTotalSum();

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.orderCode LIKE 'S%'")
    Double getTotalSumWithOrderCodeStartingWithS();
    
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.orderCode LIKE 'P%'")
    Double getTotalSumWithOrderCodeStartingWithP();
    
    @Query("SELECT DISTINCT o.date FROM Order o WHERE o.orderCode LIKE 'S%'")
    List<Date> getDistinctDatesWithOrderCodeStartingWithS();
    
    @Query("SELECT DISTINCT o.date FROM Order o WHERE o.orderCode LIKE 'P%'")
    List<Date> getDistinctDatesWithOrderCodeStartingWithP();
    
    @Query("SELECT o.date, SUM(o.total) FROM Order o WHERE o.orderCode LIKE 'S%' GROUP BY o.date")
    List<Object[]> getTotalSumByDateForOrderCodeStartingWithS();
    
    @Query("SELECT o.date, SUM(o.total) FROM Order o WHERE o.orderCode LIKE 'P%' GROUP BY o.date")
    List<Object[]> getTotalSumByDateForOrderCodeStartingWithP();
  
}