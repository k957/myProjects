package com.rest.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.LocalSearchDirectoryApplication;
import com.example.dto.MerchantDto;
import com.example.service.MerchantServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LocalSearchDirectoryApplication.class)
public class MerchantSignUpUnitTest {
	
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;

	@MockBean
	private MerchantServiceImpl merchantService;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testCreate() throws Exception {
		MerchantDto merchantDto = new MerchantDto();
		merchantDto.setName("hfkfg");
		merchantDto.setStatus('A');
		merchantDto.setDisplayName("safsd");
		merchantDto.setMailId("safsd@gmail.com");
		merchantDto.setMobileNo("832494");
		Mockito.when(merchantService.create(merchantDto)).thenReturn("Your password id: hdhdhd");

		String merchantJson = "{\"name\":\"jhsd\",\r\n" + "	\"status\":\"A\",\r\n" + "	\"displayName\":\"sas\",\r\n"
				+ "	\"mailId\":\"ssda@gmail.com\",\r\n" + "	\"mobileNo\":\"933043\"\r\n" + "}";
		/*
		 * HttpHeaders headers = new HttpHeaders(); headers.add("Authorization",
		 * "Token ");
		 */

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/LocalSearchDirectory/SignUp")
				.accept(MediaType.APPLICATION_JSON).content(merchantJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result;
		result = mvc.perform(requestBuilder).andReturn();
		assertEquals(201, result.getResponse().getStatus());

	}

	@Test
	public void testCreateWhenNullPassed() {
		MerchantDto merchantDto = new MerchantDto();
		merchantDto.setName("hfkfg");
		merchantDto.setStatus('A');
		merchantDto.setDisplayName("safsd");
		merchantDto.setMailId("safsd@gmail.com");
		merchantDto.setMobileNo("832494");
		Mockito.when(merchantService.create(merchantDto)).thenReturn("Your password id: hdhdhd");
		String merchantJson = "{\"name\":null,\r\n" + "	\"status\":\"A\",\r\n" + "	\"displayName\":null,\r\n"
				+ "	\"mailId\":\"ssda@gmail.com\",\r\n" + "	\"mobileNo\":\"933043\"\r\n" + "}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/LocalSearchDirectory/SignUp")
				.accept(MediaType.APPLICATION_JSON).content(merchantJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(400, result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
