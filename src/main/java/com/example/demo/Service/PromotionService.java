
package com.example.demo.Service;

import org.springframework.data.domain.Pageable;
import com.example.demo.Entity.Promotion;
import org.springframework.data.domain.Page;

public interface PromotionService {
  Page<Promotion> findAll(Pageable pageable);
  Promotion save(Promotion promotion);
}
