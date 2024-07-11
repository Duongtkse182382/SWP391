package com.example.demo.Repository;

import com.example.demo.Entity.GemPriceList;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GemPriceListRepository extends JpaRepository<GemPriceList, Integer> {

	List<GemPriceList> findByGemID(int id);
}