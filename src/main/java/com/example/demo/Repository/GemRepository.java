
package com.example.demo.Repository;

import com.example.demo.Entity.Gem;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GemRepository extends JpaRepository<Gem, Integer> {
	   Gem findByGemCode(String gemCode);
}
