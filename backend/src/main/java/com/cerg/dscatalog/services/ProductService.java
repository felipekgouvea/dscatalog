package com.cerg.dscatalog.services;

import java.util.Arrays;
import java.util.List;
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
import com.cerg.dscatalog.dto.ProductDTO;
import com.cerg.dscatalog.entities.Category;
import com.cerg.dscatalog.entities.Product;
import com.cerg.dscatalog.repositories.CategoryRepository;
import com.cerg.dscatalog.repositories.ProductRepository;
import com.cerg.dscatalog.services.exceptions.DataBaseException;
import com.cerg.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Long categoryId, String name, Pageable pageable){
		List<Category> categories =  (categoryId == 0) ? null : Arrays.asList(categoryRepository.getOne(categoryId));
		Page<Product> list = productRepository.find(categories, name, pageable);		
		return list.map(x -> new ProductDTO(x));  
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id){
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Categoria não exite!"));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {			
			Product entity = productRepository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not Found! ");
		}	
	}

	public void delete(Long id) {
		try {
			productRepository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + id + " not Found!");
		}catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity Violation"); 
		}
	}	

	private void copyDtoToEntity(ProductDTO dto, Product entity) {

		entity.setName(dto.getName()); 
		entity.setDescription(dto.getDescription()); 
		entity.setPrice(dto.getPrice()); 
		entity.setDate(dto.getDate()); 
		entity.setImgUrl(dto.getImgUrl());
		
		entity.getCategories().clear();
		for(CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getOne(catDto.getId());
			entity.getCategories().add(category);
		}		
	}
}
