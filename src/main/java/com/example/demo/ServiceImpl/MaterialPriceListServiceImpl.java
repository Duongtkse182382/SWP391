package com.example.demo.ServiceImpl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Material;
import com.example.demo.Entity.MaterialPriceList;
import com.example.demo.Repository.MaterialPriceListRepository;
import com.example.demo.Service.MaterialPriceListService;

@Service
public class MaterialPriceListServiceImpl implements MaterialPriceListService{
	@Autowired
	private MaterialPriceListRepository materialPriceListRepository;
	
	@Override
	public List<MaterialPriceList> getAllMaterialPriceLists() {
        return materialPriceListRepository.findAll();
    }
	
	 @Override
	    public List<MaterialPriceList> getMaterialPriceListById(int id) {
	        return materialPriceListRepository.findByMaterialID(id);
	    }
}
