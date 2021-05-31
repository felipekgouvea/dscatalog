package com.cerg.dscatalog.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cerg.dscatalog.dto.ProductDTO;
import com.cerg.dscatalog.entities.Category;
import com.cerg.dscatalog.entities.Product;
import com.cerg.dscatalog.repositories.CategoryRepository;
import com.cerg.dscatalog.repositories.ProductRepository;
import com.cerg.dscatalog.services.exceptions.DataBaseException;
import com.cerg.dscatalog.services.exceptions.ResourceNotFoundException;
import com.cerg.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private long existsId;
	private long nonExistsId;
	private long dependentId;
	private Product product;
	private PageImpl<Product> page;
	private ProductDTO productDTO; 
	private Category category;
	
	@BeforeEach 
	void setUp() throws Exception{
		existsId = 1L;
		nonExistsId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		page = new PageImpl<>(List.of(product));
		productDTO = Factory.createProductDTO();		
		category = Factory.createCategory();
		
		when(productRepository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		when(productRepository.findById(existsId)).thenReturn(Optional.of(product));
		when(productRepository.findById(nonExistsId)).thenReturn(Optional.empty());		
		
		when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
		
		when(productRepository.getOne(existsId)).thenReturn(product);
		when(productRepository.getOne(nonExistsId)).thenThrow(EntityNotFoundException.class);
		
		when(categoryRepository.getOne(existsId)).thenReturn(category);
		when(categoryRepository.getOne(nonExistsId)).thenThrow(EntityNotFoundException.class);
		
		doNothing().when(productRepository).deleteById(existsId);
		
		doThrow(ResourceNotFoundException.class).when(productRepository).deleteById(nonExistsId);
		doThrow(DataBaseException.class).when(productRepository).deleteById(dependentId);
		
		doThrow(ResourceNotFoundException.class).when(productRepository).findById(nonExistsId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			productRepository.deleteById(existsId);			
		});
		
		verify(productRepository, times(1)).deleteById(existsId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenNonExistsId() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productRepository.deleteById(nonExistsId);			
		});
		
		verify(productRepository, times(1)).deleteById(nonExistsId);
	}

	@Test
	public void deleteShouldThrowDataBaseExceptionWhenDependentId() {
		
		Assertions.assertThrows(DataBaseException.class, () -> {
			productRepository.deleteById(dependentId);			
		});
		
		verify(productRepository, times(1)).deleteById(dependentId);
	}

	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = productService.findAllPaged(pageable); 
		
		Assertions.assertNotNull(result);
		
		verify(productRepository, times(1)).findAll(pageable);
	}

	@Test
	public void findByIdShouldReturnProductDTOWhenExistsId() {
		
		productDTO = productService.findById(existsId);
		
		Assertions.assertNotNull(productDTO);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenNonExistsId() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.findById(nonExistsId);			
		});
	}

	@Test
	public void updateShouldReturnProductDTOWhenExistsId() {
		
		ProductDTO result = productService.update(existsId, productDTO);
		
		Assertions.assertNotNull(result);
	}

	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenNonExistsId() {
			
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.update(nonExistsId, productDTO);			
		});
	}
}
