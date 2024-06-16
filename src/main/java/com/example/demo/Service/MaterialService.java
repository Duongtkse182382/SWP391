package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.Entity.Material;

public interface MaterialService {
    List<Material> getAllMaterials();
    Optional<Material> getMaterialById(int id);
    Material saveMaterial(Material material);
    Material updateMaterial(Material material);
    void deleteMaterial(int id);
}