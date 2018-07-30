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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/v1/product")
@Api(value="Product Controller REST Endpoint",description="Product Controller API")
public class ProductController {

	@Autowired
	private IProductService productservice;

	@Autowired
	private IProductRepository productRepository;

	String err = "JWT Token is missing";
	Jedis jedis = new Jedis("localhost");

	@GetMapping
	@ApiOperation(value="returns the list of all Products available",response=ProductDto.class)
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
	@ApiOperation(value="returns one Product whose ID provided in the URL",response=ProductDto.class)
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
	@ApiOperation(value="method to create Product",response=ProductDto.class)
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
	@ApiOperation(value="Updates Product whose ID is provided in the URL",response=ProductDto.class)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody ProductDto productDto) {
		if (jedis.get("users::1") != null) {
			Product product = productRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Product", "product ID", id));
			product.setCreatedAt(new Date());
			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setColor(productDto.getColor());
			product.setSize(productDto.getSize());
			productRepository.save(product);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(product, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value="Updates Product whose ID is provided in the URL",response=ProductDto.class)
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
