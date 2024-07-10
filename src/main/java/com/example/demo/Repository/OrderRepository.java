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
    
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.orderCode LIKE 'S%' GROUP BY o.date")
    List<Object[]> getTotalSumByDateForOrderCodeStartingWithS();
    
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.orderCode LIKE 'P%' GROUP BY o.date")
    List<Object[]> getTotalSumByDateForOrderCodeStartingWithP();
    
    @Query("SELECT o.staffID, s.fullName, SUM(o.total) AS totalSum " +
            "FROM Order o JOIN o.staff s " +
            "GROUP BY o.staffID, s.fullName " +
            "ORDER BY totalSum DESC")
     List<Object[]> findTop5OrdersByTotal();
     
     @Query("SELECT c.counterID, c.counterName,COUNT(p) AS productCount, SUM(od.total) AS totalSum " +
    	       "FROM Product p " +
    	       "JOIN p.orderDetails od " +
    	       "JOIN p.counter c " +
    	       "WHERE p.isActive = false "+
    	       "GROUP BY c.counterID, c.counterName " +
    	       "ORDER BY totalSum DESC")
    	List<Object[]> findTopCounterByOrderTotal();
}