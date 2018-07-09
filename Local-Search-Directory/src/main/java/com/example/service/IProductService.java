package com.example.service;

import java.util.List;

import com.example.dto.ProductDto;
import com.example.model.Product;

public interface IProductService {

	List<Product> viewAll();

	Product viewOne(Long id);

	Product update(ProductDto productDto);

	void delete(List<Product> productList);

	Product create(ProductDto productDto);
}
