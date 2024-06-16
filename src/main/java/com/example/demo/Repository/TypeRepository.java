package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Type;

public interface TypeRepository extends JpaRepository<Type, Integer> {

}
