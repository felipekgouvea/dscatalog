package com.cerg.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cerg.dscatalog.dto.CategoryDTO;
import com.cerg.dscatalog.entities.Category;
import com.cerg.dscatalog.repositories.CategoryRepository;
import com.cerg.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = categoryRepository.findAll();		
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());  
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id){				
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Categoria não exite!"));
		return new CategoryDTO(entity);
	}
	
	
}
