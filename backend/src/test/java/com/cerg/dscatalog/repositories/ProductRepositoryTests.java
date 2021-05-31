package com.cerg.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.cerg.dscatalog.entities.Product;
import com.cerg.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private long existsId;
	private long noExistId;
	private long contTotalProduct;
	private Product product; 
	
	@BeforeEach 
	void setUp() throws Exception{
		existsId = 1L;
		noExistId = 100L;
		contTotalProduct = 25;
		product = Factory.createProduct();
	}	
	
	@Test
	public void findByIdShouldReturnNotEmptyObjectWhenIdExists() {
		Optional<Product> result = repository.findById(existsId);

		Assertions.assertTrue(result.isPresent());		
	}

	@Test
	public void findByIdShouldReturnEmptyObjectWhenIdNotExists() {
		Optional<Product> result = repository.findById(noExistId);

		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(contTotalProduct + 1, product.getId());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		repository.deleteById(existsId);
		Optional<Product> result = repository.findById(existsId);

		Assertions.assertFalse(result.isPresent());		
	}
	
	@Test
	public void deleteShouldEmptyResultDataAccessExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(noExistId);
		});		
	}
}
