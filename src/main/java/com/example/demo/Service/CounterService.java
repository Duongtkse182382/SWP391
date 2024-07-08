package com.example.demo.Service;

import com.example.demo.Entity.Counter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CounterService {
    Page<Counter> findPaginated(Pageable pageable);
    Counter saveCounter(Counter counter);
    List<Counter> findAll();
    Optional <Counter> getCounterById(Integer counterID);
    void updateCounter(Counter counter);
}
