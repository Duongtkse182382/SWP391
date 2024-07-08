package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Material;

@Service
public interface MaterialService {
	List<Material> getAllMaterials();
    Optional<Material> getMaterialById(int id);
    Material saveMaterial(Material material);
    Material updateMaterial(Material material);
    void deleteMaterial(int id);
    Material getMaterialByName(String name);
    Page<Material> findAllMaterialList(Pageable pageable);
}