package com.rest.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.AppConfiguration;
import com.example.LocalSearchDirectoryApplication;
import com.example.dto.MerchantDto;
import com.example.dto.ProductDto;
import com.example.model.User;
import com.example.security.JwtGenerator;
import com.example.service.IMerchantService;
import com.example.service.IProductService;
import com.example.service.IUserService;

import redis.clients.jedis.Jedis;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {LocalSearchDirectoryApplication.class,AppConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductIntegrationTest {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@Autowired
	private IMerchantService merchantService;
	
	@Autowired
	private IProductService productService;
	
	@Mock
	private Jedis jedis;

	@LocalServerPort
	private int port;
	
	@Autowired
    private TestRestTemplate testRestTemplate;
	
	public MerchantDto createMerchantfunc()
	{
		MerchantDto merchantDto=new MerchantDto();
		merchantDto.setName("kanav");
		merchantDto.setDisplayName("kkk");
		merchantDto.setMailId("kkk@gmail.com");
		merchantDto.setMobileNo("7509865");
		merchantDto.setStatus('A');
		return merchantDto;
	}
	
	public ProductDto createProduct()
	{
		ProductDto productDto=new ProductDto();
		productDto.setMerchantId(2l);
		productDto.setName("T-Shirts");
		productDto.setDescription("Puma");
		productDto.setColor("blue");
		productDto.setSize("M");
		return productDto;
	}
	
	@Test
	public void testViewAll() {
		String url = "http://localhost:" + port + "/v1/product";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		//System.out.println+"=================merchantDto");
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		System.out.println(user);
		String token=jwtGenerator.generate(user);
		System.out.println("token===="+token);
		ProductDto productDto=createProduct();
		productService.create(productDto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
	
		HttpEntity<Object> entity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> response = testRestTemplate.exchange(url, HttpMethod.GET,entity,Object.class);
		
        assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	public void testViewOne() {
		String url = "http://localhost:" + port + "/v1/product/3";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		System.out.println(user);
		String token=jwtGenerator.generate(user);
		System.out.println("token===="+token);
		
		ProductDto productDto=createProduct();
		productService.create(productDto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
	
		HttpEntity<Object> entity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> response = testRestTemplate.exchange(url, HttpMethod.GET,entity,Object.class);
		
        assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	public void testCreate() {
		String url = "http://localhost:" + port + "/v1/product";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		String token=jwtGenerator.generate(user);
		
		
		ProductDto productDto=createProduct();
		productService.create(productDto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
		
		HttpEntity<ProductDto> entity = new HttpEntity<>(productDto, headers);
		ResponseEntity<ProductDto> response = testRestTemplate.exchange(url, HttpMethod.POST,entity,ProductDto.class);
		
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        
	}

	@Test
	public void testUpdate() {
		String url = "http://localhost:" + port + "/v1/product/3";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		String token=jwtGenerator.generate(user);
		
		
		ProductDto productDto=createProduct();
		productService.create(productDto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
		
		HttpEntity<ProductDto> entity = new HttpEntity<>(productDto, headers);
		ResponseEntity<ProductDto> response = testRestTemplate.exchange(url, HttpMethod.PUT,entity,ProductDto.class);
		
        assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	public void testDelete() {
		String url = "http://localhost:" + port + "/v1/product/3";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		String token=jwtGenerator.generate(user);
		
		
		ProductDto productDto=createProduct();
		productService.create(productDto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
		
		HttpEntity<ProductDto> entity = new HttpEntity<>(productDto, headers);
		ResponseEntity<ProductDto> response = testRestTemplate.exchange(url, HttpMethod.DELETE,entity,ProductDto.class);
		
        assertEquals(HttpStatus.OK,response.getStatusCode());
	}

}
