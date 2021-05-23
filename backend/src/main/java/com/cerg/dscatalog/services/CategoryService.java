package com.cerg.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cerg.dscatalog.dto.CategoryDTO;
import com.cerg.dscatalog.entities.Category;
import com.cerg.dscatalog.repositories.CategoryRepository;
import com.cerg.dscatalog.services.exceptions.DataBaseException;
import com.cerg.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable){
		Page<Category> list = categoryRepository.findAll(pageable);		
		return list.map(x -> new CategoryDTO(x));  
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id){				
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Categoria não exite!"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category newCategory = new Category();
		newCategory.setName(dto.getName());
		newCategory = categoryRepository.save(newCategory);
		return new CategoryDTO(newCategory);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {			
			Category entity = categoryRepository.getOne(id);
			entity.setName(dto.getName());
			entity = categoryRepository.save(entity);
			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not Found! ");
		}	
	}

	public void delete(Long id) {
		try {
			categoryRepository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + id + " not Found!");
		}catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity Violation"); 
		}
	}
}
