package com.example.demo.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.Entity.Gem;

public interface GemService {
    List<Gem> getAllGems();
    Gem getGemById(int id);
    Gem saveGem(Gem gem);
    void deleteGemById(int id);
    Gem getGemByGemCode(String gemCode);
}