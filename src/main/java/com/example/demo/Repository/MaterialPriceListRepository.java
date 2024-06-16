package com.example.demo.Repository;

import com.example.demo.Entity.MaterialPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialPriceListRepository extends JpaRepository<MaterialPriceList, Integer> {
}
