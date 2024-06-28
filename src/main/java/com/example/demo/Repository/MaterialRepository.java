package com.example.demo.Repository;

import com.example.demo.Entity.Material;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

	Optional<Material> findByName(String name);


}
