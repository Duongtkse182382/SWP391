package com.example.demo.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.demo.Entity.GemPriceList;
public interface GemPriceListService {
   List<GemPriceList>  getGemPriceListById(int id);
   Optional<GemPriceList>  findGemPriceListById(Integer id);
   List<GemPriceList> getAllGemPriceLists();
   Page<GemPriceList> findAllGemPriceList(Pageable pageable);
    GemPriceList save(GemPriceList gemPriceList);
    GemPriceList update(GemPriceList gemPriceList);
}
