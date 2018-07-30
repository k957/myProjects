package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assembler.ProductAssembler;
import com.example.dto.ProductDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Product;
import com.example.repository.IProductRepository;

@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private ProductAssembler productAssembler;

	@Autowired
	private IProductRepository productRepository;

	@Override
	public List<Product> viewAll() {
		List<Product> product = productRepository.findAll();
		return product;
	}

	@Override
	public Product viewOne(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "product_id", id));
		return product;
	}

	@Override
	public Product update(ProductDto productDto) {

		return null;
	}

	@Override
	public Product create(ProductDto productDto) {
		Product product = productAssembler.createProductEntity(productDto);
		product.setCreatedAt(new java.util.Date());
		productRepository.save(product);
//		System.out.println(product.getId());
		return product;
	}

	@Override
	public void delete(List<Product> product) {
		//declared in controller
	}

}
