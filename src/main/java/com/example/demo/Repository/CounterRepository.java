package com.example.demo.Repository;

import com.example.demo.Entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Integer> {
	  boolean existsByCounterName(String counterName);
	  Counter findByCounterName(String counterName);
}
