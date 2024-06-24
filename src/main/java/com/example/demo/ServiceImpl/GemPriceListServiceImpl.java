package com.example.demo.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.GemPriceList;
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
   
}
