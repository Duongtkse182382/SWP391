package com.example.demo.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.Entity.Gem;

public interface GemService {
	List<Gem> getAllGems();
    Gem getGemById(int id);
    Gem saveGem(Gem gem);
    void deleteGemById(int id);
    Gem getGemByGemCode(String gemCode);
	Page<Gem> findAllGemList(Pageable pagable);
	Optional<Gem> findGemById(int id);
    Gem updateGem(Gem gem);
}