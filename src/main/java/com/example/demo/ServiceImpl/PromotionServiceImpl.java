package com.example.demo.ServiceImpl;

import java.util.List;
import java.util.Optional;

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
       public Promotion save(Promotion promotion) {
           if (promotionRepository.existsByPromotionCode(promotion.getPromotionCode())) {
               throw new IllegalArgumentException("Promotion code already exists");
           }
           if (promotion.getStartDate().compareTo(promotion.getEndDate()) >= 0) {
               throw new IllegalArgumentException("Start date must be before end date");
           }
           String promotionPercent = promotion.getPromotionPercent();
           if (promotionPercent == null || promotionPercent.isEmpty()) {
               throw new IllegalArgumentException("Promotion percent must be provided");
           }
           if (!promotionPercent.matches("^\\d+(\\.\\d+)?%$")) {
               throw new IllegalArgumentException("Invalid promotion percent format");
           }
           double percentValue = Double.parseDouble(promotionPercent.replace("%", "").trim());
           if (percentValue <= 0) {
               throw new IllegalArgumentException("Promotion percent must be greater than 0%");
           }
           promotion.setStatus(true);
           return promotionRepository.save(promotion);
       }
       @Override
       public List<Promotion> findAll() {
           return promotionRepository.findAll();
       }
	@Override
	public Optional<Promotion> findByPromotionID(int id) {
		
		return promotionRepository.findById(id);
	}
	@Override
	public Promotion update(Promotion promotion) {
		 if (promotion.getStartDate().compareTo(promotion.getEndDate()) >= 0) {
             throw new IllegalArgumentException("Start date must be before end date");
         }
		 String promotionPercent = promotion.getPromotionPercent();
         if (promotionPercent == null || promotionPercent.isEmpty()) {
             throw new IllegalArgumentException("Promotion percent must be provided");
         }
         if (!promotionPercent.matches("^\\d+(\\.\\d+)?%$")) {
             throw new IllegalArgumentException("Invalid promotion percent format");
         }
         double percentValue = Double.parseDouble(promotionPercent.replace("%", "").trim());
         if (percentValue <= 0) {
             throw new IllegalArgumentException("Promotion percent must be greater than 0%");
         }
		return promotionRepository.save(promotion);
	}
}
