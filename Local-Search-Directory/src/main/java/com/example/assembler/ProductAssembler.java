package com.example.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dto.ProductDto;
import com.example.model.Merchant;
import com.example.model.Product;
import com.example.repository.IMerchantRepository;

@Component
public class ProductAssembler {
	@Autowired
	private IMerchantRepository merchantRepository;
	
	public List<Product> createProductEntity(List<ProductDto> productDtoList) {
		
		List<Product> productList = new ArrayList<>();
		productDtoList.forEach(productDto -> {
			Product product = new Product();
			Merchant merchant = merchantRepository.getOne(productDto.getMerchantId());
			product.setMerchant(merchant);
			product.setName(productDto.getName());
			product.setColor(productDto.getColor());
			product.setSize(productDto.getSize());
			product.setDescription(productDto.getDescription());

			productList.add(product);
		});

		return productList;
	}
	
	public Product createProductEntity(ProductDto productDto) {
		Product product = new Product();
		Merchant merchant = merchantRepository.getOne(productDto.getMerchantId());
		product.setMerchant(merchant);
		product.setName(productDto.getName());
		product.setColor(productDto.getColor());
		product.setSize(productDto.getSize());
		product.setDescription(productDto.getDescription());
		return product;
	}
}
