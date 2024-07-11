package com.example.demo.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.GemPriceList;
import com.example.demo.Entity.MaterialPriceList;
import com.example.demo.Repository.GemPriceListRepository;
import com.example.demo.Service.GemPriceListService;

@Service
public class GemPriceListServiceImpl implements GemPriceListService {
   @Autowired
   private GemPriceListRepository gemPriceListRepository;
   
   @Override
   public List<GemPriceList> getAllGemPriceLists() {
       return gemPriceListRepository.findAll();
   }
   
   @Override
   public Page<GemPriceList> findAllGemPriceList(Pageable pageable) {
       return gemPriceListRepository.findAll(pageable);
   }
   @Override
   public GemPriceList save(GemPriceList gemPriceList) {
       return gemPriceListRepository.save(gemPriceList);
   }
   @Override
   public List<GemPriceList> getGemPriceListById(int id) {
       return gemPriceListRepository.findByGemID(id);
   }
   @Override
   public Optional<GemPriceList> findGemPriceListById(Integer id) {
       return gemPriceListRepository.findById(id);
   }
   @Override
   public GemPriceList update(GemPriceList gemPriceList) {
         return gemPriceListRepository.save(gemPriceList);
   }
}
