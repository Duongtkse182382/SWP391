package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.demo.Entity.MaterialPriceList;


public interface MaterialPriceListService {
   List<MaterialPriceList> getAllMaterialPriceLists();
   List<MaterialPriceList>  getMaterialPriceListById(int id);
   Optional<MaterialPriceList> findMaterialPriceListById(Integer id);
   Page<MaterialPriceList> findAllMaterial(Pageable pageable);
   MaterialPriceList save(MaterialPriceList materialPriceList);
   MaterialPriceList update(MaterialPriceList materialPriceList);
   
}
