package com.cerg.dscatalog.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cerg.dscatalog.entities.Category;
import com.cerg.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll(){
		List<Category> list = new ArrayList<>();		
		list = categoryService.findAll();
		return ResponseEntity.ok().body(list);
	}

}
