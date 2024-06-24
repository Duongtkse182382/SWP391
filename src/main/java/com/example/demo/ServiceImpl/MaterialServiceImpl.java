package com.example.demo.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Material;
import com.example.demo.Repository.MaterialRepository;
import com.example.demo.Service.MaterialService;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    @Override
    public Optional<Material> getMaterialById(int id) {
        return materialRepository.findById(id);
    }

    @Override
    public Material saveMaterial(Material material) {
        return materialRepository.save(material);
    }
    @Override
    public Material getMaterialByName(String name) {
        return materialRepository.findByName(name).orElse(null);
    }

    @Override
    public Material updateMaterial(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public void deleteMaterial(int id) {
        materialRepository.deleteById(id);
    }
}