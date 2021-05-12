package com.cerg.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cerg.dscatalog.entities.Category;
import com.cerg.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
	
	
}
