package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Counter;
import com.example.demo.Exception.DuplicateCounterNameException;
import com.example.demo.Repository.CounterRepository;
import com.example.demo.Service.CounterService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterRepository counterRepository;

    @Override
    public Page<Counter> findPaginated(Pageable pageable) {
        return counterRepository.findAll(pageable);
    }
    @Override
    public Counter saveCounter(Counter counter) {
    	 if (counterRepository.existsByCounterName(counter.getCounterName())) {
             throw new DuplicateCounterNameException("Counter name already exists");
         }
        return counterRepository.save(counter);
    }
    @Override 
    public List<Counter> findAll(){
    	return counterRepository.findAll();
    }
    @Override
    public Optional<Counter> getCounterById(Integer counterID) {
        return counterRepository.findById(counterID);
    }

    public void updateCounter(Counter counter) {
        Counter existingCounter = counterRepository.findByCounterName(counter.getCounterName());
        if (existingCounter != null && !existingCounter.getCounterID().equals(counter.getCounterID())) {
            throw new DuplicateCounterNameException("Counter name already exists.");
        }
        counterRepository.save(counter);
    }

}
