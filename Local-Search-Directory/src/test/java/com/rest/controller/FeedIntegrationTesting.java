package com.rest.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.example.LocalSearchDirectoryApplication;
import com.example.assembler.PaymentMethodAssembler;
import com.example.dto.FeedDto;
import com.example.dto.MerchantDto;
import com.example.dto.PaymentMethodDto;
import com.example.dto.ProductDto;
import com.example.dto.StoreDto;
import com.example.model.PaymentMethod;
import com.example.model.User;
import com.example.security.JwtGenerator;
import com.example.service.IFeedService;
import com.example.service.IMerchantService;
import com.example.service.IPaymentMethodService;
import com.example.service.IProductService;
import com.example.service.IStoreService;
import com.example.service.IUserService;

import redis.clients.jedis.Jedis;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {LocalSearchDirectoryApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class FeedIntegrationTesting {
	
	@Autowired
	private IFeedService feedService;
	
	@Autowired
	private IPaymentMethodService paymentService;
	
	@Autowired
	private IStoreService storeService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@Autowired
	private IMerchantService merchantService;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private PaymentMethodAssembler paymentMethodAssembler;
	
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
	
	public PaymentMethodDto createPaymentMethod(){
		PaymentMethodDto paymentMethodDto=new PaymentMethodDto();
		paymentMethodDto.setCode("107");
		paymentMethodDto.setName("Paytm");
		return paymentMethodDto;
	}
	public StoreDto createStore() {
		List<PaymentMethodDto> paymentMethod=new ArrayList<>();
		paymentMethod.add(createPaymentMethod());
		List<PaymentMethod> payment=paymentMethodAssembler.createPaymentEntity(paymentMethod);
		StoreDto storeDto=new StoreDto();
		storeDto.setMerchantId(2l);
		storeDto.setName("kobe");
		storeDto.setDescription("fine dine");
		storeDto.setPostalCode("143001");
		storeDto.setAddress("amritsar");
		storeDto.setPhone("6235");
		storeDto.setLatitude(26.70);
		storeDto.setLongitude(77.10);
		storeDto.setOpeningHours("9");
		storeDto.setPaymentMethodId(payment);
		return storeDto;
	}
	
	public FeedDto createFeed() {
		FeedDto feedDto=new FeedDto();
		feedDto.setMerchantId(2l);
		feedDto.setProductId(3l);
		feedDto.setStoreId(5l);
		feedDto.setPrice(110);
		feedDto.setQuantity(1);
		feedDto.setSalePrice(100);
		feedDto.setStartDate(null);
		feedDto.setEndDate(null);
		return feedDto;
	}
	
	@Test
	public void testViewAll() throws IOException {
		String url = "http://localhost:" + port + "/v1/feed";
		
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		
		String token=jwtGenerator.generate(user);
		
		
		ProductDto productDto=createProduct();
		productService.create(productDto);
		
		PaymentMethodDto paymentMethodDto=createPaymentMethod();
		paymentService.create(paymentMethodDto);
		
		StoreDto storeDto=createStore();
		storeService.create(storeDto);
		
		FeedDto feedDto=createFeed();
		feedService.create(feedDto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
	
		HttpEntity<Object> entity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> response = testRestTemplate.exchange(url, HttpMethod.GET,entity,Object.class);
		
        assertEquals(HttpStatus.OK,response.getStatusCode());
			
	}
////////////////////////////////////////////////testviewone cases////////////////////////////////////////////
	@Test
	public void testViewOone() {
		String url = "http://localhost:" + port + "/v1/feed/7";
		
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		
		String token=jwtGenerator.generate(user);
		
		ProductDto productDto=createProduct();
		productService.create(productDto);
		
		PaymentMethodDto paymentMethodDto=createPaymentMethod();
		paymentService.create(paymentMethodDto);
		
		StoreDto storeDto=createStore();
		storeService.create(storeDto);
		
		FeedDto feedDto=createFeed();
		feedService.create(feedDto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
	
		HttpEntity<Object> entity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> response = testRestTemplate.exchange(url, HttpMethod.GET,entity,Object.class);
		
        assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	public void creatingFeedSuccessfullyWillReturn201ResponseCode() {
		String url = "http://localhost:" + port + "/v1/feed";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		System.out.println(password);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		String token=jwtGenerator.generate(user);
		
		ProductDto productDto=createProduct();
		productService.create(productDto);
		
		PaymentMethodDto paymentMethodDto=createPaymentMethod();
		paymentService.create(paymentMethodDto);
		
		StoreDto storeDto=createStore();
		storeService.create(storeDto);
		
		FeedDto feedDto=createFeed();
		feedService.create(feedDto);
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
		
		
		HttpEntity<FeedDto> entity = new HttpEntity<>(feedDto, headers);
		ResponseEntity<FeedDto> response = testRestTemplate.exchange(url, HttpMethod.POST,entity,FeedDto.class);
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
	
	}

	@Test
	public void testUpdate() {
		
		String url = "http://localhost:" + port + "/v1/feed/7";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		String token=jwtGenerator.generate(user);
		
		ProductDto productDto=createProduct();
		productService.create(productDto);
		
		PaymentMethodDto paymentMethodDto=createPaymentMethod();
		paymentService.create(paymentMethodDto);
		
		StoreDto storeDto=createStore();
		storeService.create(storeDto);
		
		FeedDto feedDto=createFeed();
		feedService.create(feedDto);
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);

		HttpEntity<FeedDto> entity = new HttpEntity<>(feedDto, headers);
		ResponseEntity<FeedDto> response = testRestTemplate.exchange(url, HttpMethod.PUT,entity, FeedDto.class);
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		
	}

	@Test
	public void testDelete() {
		
		String url="http://localhost:"+ port +"/v1/feed/7";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser(merchantDto.getMailId(), password);
		String token = jwtGenerator.generate(user);
		
		ProductDto productDto=createProduct();
		productService.create(productDto);
		
		PaymentMethodDto paymentMethodDto=createPaymentMethod();
		paymentService.create(paymentMethodDto);
		
		StoreDto storeDto=createStore();
		storeService.create(storeDto);
		
		FeedDto feedDto=createFeed();
		feedService.create(feedDto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Token "+token);
		
		HttpEntity<FeedDto> entity = new HttpEntity<>(feedDto,headers);
		ResponseEntity<FeedDto> response = testRestTemplate.exchange(url, HttpMethod.DELETE,entity,FeedDto.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
