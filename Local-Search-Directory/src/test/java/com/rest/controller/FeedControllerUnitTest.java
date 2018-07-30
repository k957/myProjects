package com.rest.controller;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.LocalSearchDirectoryApplication;
import com.example.model.User;
import com.example.service.FeedServiceImpl;
import com.example.service.UserServiceImpl;

import ai.grakn.redismock.RedisServer;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LocalSearchDirectoryApplication.class)
public class FeedControllerUnitTest {

	@MockBean
	private UserServiceImpl userService;

	@MockBean
	private Jedis jedis;

	@MockBean
	private FeedServiceImpl FeedService;

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	//------------------------------------view All tests------------------------------------------------//
	@Test
	public void testViewAll() throws IOException {
		User user = new User();
		RedisServer server=RedisServer.newRedisServer();
		server.start();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());

		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/feed").headers(headers)
				.contentType(MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mvc.perform(requestBuilder).andReturn();
			assertEquals(200, result.getResponse().getStatus());
			assertEquals("application/json;charset=UTF-8", result.getResponse().getContentType());

		} catch (Exception e) {

			e.printStackTrace();
		}
		  server.stop();
	}
	
	@Test
	public void testViewAllWhenNoToken() {
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token ");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/feed").headers(headers)
				.contentType(MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mvc.perform(requestBuilder).andReturn();
			assertEquals(401, result.getResponse().getStatus());
			assertEquals("text/plain;charset=ISO-8859-1", result.getResponse().getContentType());

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	//------------------------------------view one tests------------------------------------------------//
	@Test
	public void testViewOne() {
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/feed/47").headers(headers)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(200, result.getResponse().getStatus());
			assertEquals("application/json;charset=UTF-8", result.getResponse().getContentType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//------------------------------------create tests------------------------------------------------//
	@Test
	public void testCreate() {
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");
	
		String feedJson = "{\n" + 
				"	\"merchantId\":6,\n" + 
				"	\"productId\":61,\n" + 
				"	\"storeId\":60,\n" + 
				"	\"price\":150.00,\n" + 
				"	\"quantity\":1,\n" + 
				"	\"sale_price\":125,\n" + 
				"	\"start_date\":\"07/05/2018\",\n" + 
				"	\"end_date\":\"07/05/2018\"\n" + 
				"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/feed").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(feedJson).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(201, result.getResponse().getStatus());
			//assertEquals("application/json;charset=UTF-8", result.getResponse().getContentType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateWhenNoDataPassedInBody() {
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");

		String feedJson = "{ }";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/feed").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(feedJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(400, result.getResponse().getStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateInvalidData() {
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");
		
		String feedJson = "{\n" + 
				"	\"merchantId\":\"6\",\n" + 
				"	\"productId\":\"two\",\n" + 
				"	\"storeId\":60,\n" + 
				"	\"price\":-25,\n" + 
				"	\"quantity\":null,\n" + 
				"	\"sale_price\":125,\n" + 
				"	\"start_date\":\"07/05/2018\",\n" + 
				"	\"end_date\":\"07/05/2018\"\n" + 
				"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/feed").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(feedJson).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(400, result.getResponse().getStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testCreateNullData() {
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");
		
		String feedJson = "{\n" + 
				"	\"merchantId\":null,\n" + 
				"	\"productId\":null,\n" + 
				"	\"storeId\":60,\n" + 
				"	\"price\":null,\n" + 
				"	\"quantity\":null,\n" + 
				"	\"sale_price\":125,\n" + 
				"	\"start_date\":\"07/05/2018\",\n" + 
				"	\"end_date\":\"07/05/2018\"\n" + 
				"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/feed").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(feedJson).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(400, result.getResponse().getStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//------------------------------------updates tests------------------------------------------------//
	@Test
	public void testUpdate() {
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");

		String feedJson = "{\n" + 
				"	\"merchantId\":6,\n" + 
				"	\"productId\":61,\n" + 
				"	\"storeId\":60,\n" + 
				"	\"price\":150.00,\n" + 
				"	\"quantity\":1,\n" + 
				"	\"sale_price\":125,\n" + 
				"	\"start_date\":\"07/05/2018\",\n" + 
				"	\"end_date\":\"07/05/2018\"\n" + 
				"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/feed/47").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(feedJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(201, result.getResponse().getStatus());
			assertEquals("application/json;charset=UTF-8", result.getResponse().getContentType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateWhenNoIdInUrl() {
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");

		String feedJson = "{\n" + 
				"	\"merchantId\":6,\n" + 
				"	\"productId\":61,\n" + 
				"	\"storeId\":60,\n" + 
				"	\"price\":150.00,\n" + 
				"	\"quantity\":1,\n" + 
				"	\"sale_price\":125,\n" + 
				"	\"start_date\":\"07/05/2018\",\n" + 
				"	\"end_date\":\"07/05/2018\"\n" + 
				"}";
		;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/feed/").accept(MediaType.APPLICATION_JSON)
				.content(feedJson).contentType(MediaType.APPLICATION_JSON).headers(headers);

		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(405, result.getResponse().getStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateWhenNoDataPassedInBody() {
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");

		String merchantJson = "{ }";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/feed/47").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(merchantJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(400, result.getResponse().getStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//------------------------------------delete function tests------------------------------------------------//
	@Test
	public void testDelete() {
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/feed/47").headers(headers);
		
		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(200, result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
