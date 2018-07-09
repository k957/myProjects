package com.example.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ProductDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Product;
import com.example.repository.IProductRepository;
import com.example.service.IProductService;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

	@Autowired
	private IProductService productservice;

	@Autowired
	private IProductRepository productRepository;

	String err = "JWT Token is missing";
	Jedis jedis = new Jedis("localhost");

	@GetMapping
	public ResponseEntity<?> viewAll() {
		if (jedis.get("users::1") != null) {
			List<Product> product = productservice.viewAll();
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(product, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> viewOne(@PathVariable("id") Long id) {
		if (jedis.get("users::1") != null) {
			Product product = productservice.viewOne(id);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(product, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody ProductDto productDto) {
		if (jedis.get("users::1") != null) {
			
			Product product = productservice.create(productDto);

			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(product, responseHeader, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody ProductDto productDto) {
		if (jedis.get("users::1") != null) {
			Product product = productRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Product", "product ID", id));
			product.setCreated_at(new Date());
			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setColor(productDto.getColor());
			product.setSize(productDto.getSize());
			// product.setMerchant(merchant.);
			productRepository.save(product);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(product, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		if (jedis.get("users::1") != null) {
			Product product = productRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Product", "product ID", id));
			productRepository.delete(product);
			return ResponseEntity.ok().build();
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}
}
