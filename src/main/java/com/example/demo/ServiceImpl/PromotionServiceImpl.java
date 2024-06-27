package com.example.demo.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.Entity.Promotion;
import com.example.demo.Repository.PromotionRepository;
import com.example.demo.Service.PromotionService;
import org.springframework.data.domain.Page;

@Service
public class PromotionServiceImpl implements PromotionService {
	@Autowired 
	private PromotionRepository promotionRepository;
  
    public PromotionServiceImpl(PromotionRepository promotionRepository) {
    	this.promotionRepository = promotionRepository;
    }
       @Override
	    public Page<Promotion> findAll(Pageable pageable) {
	        return promotionRepository.findAll(pageable);
	    }
       @Override
       public Promotion save(Promotion promotion) {
    	   promotion.setStatus(true);
    	   return promotionRepository.save(promotion);
       }
       @Override
       public List<Promotion> findAll() {
           return promotionRepository.findAll();
       }
}
