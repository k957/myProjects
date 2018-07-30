package com.rest.controller;

import static org.junit.Assert.*;

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

import com.example.AppConfiguration;
import com.example.LocalSearchDirectoryApplication;
import com.example.assembler.PaymentMethodAssembler;
import com.example.dto.MerchantDto;
import com.example.dto.PaymentMethodDto;
import com.example.dto.StoreDto;
import com.example.model.PaymentMethod;
import com.example.model.User;
import com.example.security.JwtGenerator;
import com.example.service.IMerchantService;
import com.example.service.IPaymentMethodService;
import com.example.service.IStoreService;
import com.example.service.IUserService;

import redis.clients.jedis.Jedis;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {LocalSearchDirectoryApplication.class,AppConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentMethodIntegrationTest {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@Autowired
	private IMerchantService merchantService;
	
	@Autowired
	private IPaymentMethodService paymentService;
	
	@Autowired
	private IStoreService storeService;
	
	@Autowired
	private PaymentMethodAssembler paymentMethodAssembler;
	
	@Mock
	private Jedis jedis;

	@LocalServerPort
	private int port;
	
	@Autowired
    private TestRestTemplate testRestTemplate;
	
	public MerchantDto createMerchantfunc(){
		MerchantDto merchantDto=new MerchantDto();
		merchantDto.setName("kanav");
		merchantDto.setDisplayName("kkk");
		merchantDto.setMailId("kkk@gmail.com");
		merchantDto.setMobileNo("7509865");
		merchantDto.setStatus('A');
		return merchantDto;
	}
	
	public PaymentMethodDto createPaymentMethod(){
		PaymentMethodDto paymentMethodDto=new PaymentMethodDto();
		paymentMethodDto.setCode("107");
		paymentMethodDto.setName("Paytm");
		//paymentMethodDto.setStoreId(null);
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
	
	@Test
	public void testViewAll() {
		String url = "http://localhost:" + port + "/v1/PaymentMethod";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		//System.out.println+"=================merchantDto");
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		System.out.println(user);
		
		String token=jwtGenerator.generate(user);
		System.out.println("token===="+token);
		
		
		StoreDto storeDto=createStore();
		storeService.create(storeDto);
		
		PaymentMethodDto paymentMethodDto=createPaymentMethod();
		paymentService.create(paymentMethodDto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
	
		HttpEntity<Object> entity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> response = testRestTemplate.exchange(url, HttpMethod.GET,entity,Object.class);
		
        assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	public void testViewOne() {
		String url = "http://localhost:" + port + "/v1/PaymentMethod/4";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		System.out.println(user);
		String token=jwtGenerator.generate(user);
		System.out.println("token===="+token);
		
		StoreDto storeDto=createStore();
		storeService.create(storeDto);
		
		PaymentMethodDto paymentMethodDto=createPaymentMethod();
		paymentService.create(paymentMethodDto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","kkk@gmail.com");
	
		HttpEntity<Object> entity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> response = testRestTemplate.exchange(url, HttpMethod.GET,entity,Object.class);
		
        assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	public void testCreate() {
		String url = "http://localhost:" + port + "/v1/PaymentMethod";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		String token=jwtGenerator.generate(user);
		
		
		PaymentMethodDto paymentMethodDto=createPaymentMethod();
		paymentService.create(paymentMethodDto);
		
		StoreDto storeDto=createStore();
		storeService.create(storeDto);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
		
		HttpEntity<PaymentMethodDto> entity = new HttpEntity<>(paymentMethodDto, headers);
		ResponseEntity<PaymentMethodDto> response = testRestTemplate.exchange(url, HttpMethod.POST,entity,PaymentMethodDto.class);
		
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        
	}

	@Test
	public void testUpdate() {
		String url = "http://localhost:" + port + "/v1/PaymentMethod/3";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		String token=jwtGenerator.generate(user);
		
		PaymentMethodDto paymentMethodDto=createPaymentMethod();
		paymentService.create(paymentMethodDto);
		
		StoreDto storeDto=createStore();
		storeService.create(storeDto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
		
		HttpEntity<PaymentMethodDto> entity = new HttpEntity<>(paymentMethodDto, headers);
		ResponseEntity<PaymentMethodDto> response = testRestTemplate.exchange(url, HttpMethod.PUT,entity,PaymentMethodDto.class);
		
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
	}

	@Test
	public void testDelete() {
		String url = "http://localhost:" + port + "/v1/PaymentMethod/3";
		MerchantDto merchantDto=createMerchantfunc();
		String password=merchantService.create(merchantDto);
		User user = new User();
		user=userService.loginUser("kkk@gmail.com", password);
		String token=jwtGenerator.generate(user);
		
		
		PaymentMethodDto paymentMethodDto=createPaymentMethod();
		paymentService.create(paymentMethodDto);
		
		StoreDto storeDto=createStore();
		storeService.create(storeDto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Token "+token);
		
		HttpEntity<PaymentMethodDto> entity = new HttpEntity<>(paymentMethodDto, headers);
		ResponseEntity<PaymentMethodDto> response = testRestTemplate.exchange(url, HttpMethod.DELETE,entity,PaymentMethodDto.class);
		
        assertEquals(HttpStatus.OK,response.getStatusCode());
	}

}
