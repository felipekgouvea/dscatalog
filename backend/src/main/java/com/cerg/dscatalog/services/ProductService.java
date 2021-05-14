package com.cerg.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cerg.dscatalog.dto.ProductDTO;
import com.cerg.dscatalog.entities.Product;
import com.cerg.dscatalog.repositories.ProductRepository;
import com.cerg.dscatalog.services.exceptions.DataBaseException;
import com.cerg.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product> list = productRepository.findAll(pageRequest);		
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
		Product newProduct = new Product();
		newProduct.setName(dto.getName());
		newProduct = productRepository.save(newProduct);
		return new ProductDTO(newProduct);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {			
			Product entity = productRepository.getOne(id);
			entity.setName(dto.getName());
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
}
