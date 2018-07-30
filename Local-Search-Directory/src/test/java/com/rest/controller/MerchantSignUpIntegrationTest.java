package com.rest.controller;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import com.example.controller.MerchantSignupController;
import com.example.dto.MerchantDto;
import com.example.service.IMerchantService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {LocalSearchDirectoryApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class MerchantSignUpIntegrationTest {
		
	@Mock
    private IMerchantService merchantService;
	
	@InjectMocks
	private MerchantSignupController merchantSignupController;
	
	@LocalServerPort
	private int port;
	
	@Autowired
    private TestRestTemplate testRestTemplate;
	
	@Test
	public void canaryTest() {
		assert(true);
	}
	
	@Test
	public void testCreate() throws Exception {
		String url = "http://localhost:" + port + "/LocalSearchDirectory/SignUp";
		MerchantDto merchantDto=new MerchantDto();
		merchantDto.setName("jhsdkf");
		merchantDto.setStatus('A');
		merchantDto.setDisplayName("saih");
		merchantDto.setMailId("s@gmail.com");
		merchantDto.setMobileNo("832494");
		Mockito.when(merchantService.create(merchantDto)).thenReturn("hdhdhd");

		 HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Token ");
	    
		 HttpEntity<MerchantDto> entity = new HttpEntity<>(merchantDto, headers);


	        ResponseEntity<String> response = testRestTemplate.exchange(
	                url,
	                HttpMethod.POST, entity, String.class);
	        assertEquals(HttpStatus.CREATED,response.getStatusCode());
		
		
	}
	
	@Test
	public void testCreateNullValue() {
		String url = "http://localhost:" + port + "/LocalSearchDirectory/SignUp";
		MerchantDto merchantDto=new MerchantDto();
		merchantDto.setName(null);
		merchantDto.setStatus('\0');
		merchantDto.setDisplayName(null);
		merchantDto.setMailId(null);
		merchantDto.setMobileNo("832494");
		Mockito.when(merchantService.create(merchantDto)).thenReturn("password");
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("Authorization", "Token ");
		
		 HttpEntity<MerchantDto> entity = new HttpEntity<>(merchantDto, headers);
		 ResponseEntity<String> response = testRestTemplate.exchange(
	                url,
	                HttpMethod.POST, entity, String.class);
	        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
	}

}
