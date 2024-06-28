package com.example.demo.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Type;
import com.example.demo.Repository.TypeRepository;
import com.example.demo.Service.TypeService;

@Service
public class TypeServiceImpl implements TypeService {
	  @Autowired
	    private TypeRepository typeRepository;
	  
	  @Override
	  public List<Type> getAllTypes() {
	        return typeRepository.findAll();
	    }
}
