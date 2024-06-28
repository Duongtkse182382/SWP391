package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.Entity.Material;
import com.example.demo.Entity.MaterialPriceList;

public interface MaterialPriceListService {
   List<MaterialPriceList> getAllMaterialPriceLists();
   List<MaterialPriceList>  getMaterialPriceListById(int id);
}
