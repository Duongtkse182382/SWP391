package com.example.demo.ServiceImpl;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Gem;
import com.example.demo.Repository.GemRepository;
import com.example.demo.Service.GemService;

import java.util.List;
import java.util.Optional;
@Service
public class GemServiceImpl implements GemService{
	private final GemRepository gemRepository;

    public GemServiceImpl(GemRepository gemRepository) {
        this.gemRepository = gemRepository;
    }

    @Override
    public List<Gem> getAllGems() {
        return gemRepository.findAll();
    }

    @Override
    public Gem getGemById(int id) {
        return gemRepository.findById(id).orElse(null);
    }

    @Override
    public Gem saveGem(Gem gem) {
        return gemRepository.save(gem);
    }

    @Override
    public void deleteGemById(int id) {
        gemRepository.deleteById(id);
    }
    
    @Override
    public Gem getGemByGemCode(String gemCode) {
        return gemRepository.findByGemCode(gemCode);
    }
}
