
package com.rest.controller;

import static org.junit.Assert.*;

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
import com.example.service.StoreServiceImpl;
import com.example.service.UserServiceImpl;

import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LocalSearchDirectoryApplication.class)
public class StoreControllerUnitTest {

	@MockBean
	private UserServiceImpl userService;

	@MockBean
	private Jedis jedis;

	@MockBean
	private StoreServiceImpl storeService;

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	//------------------------------------view All tests------------------------------------------------//
	@Test
	public void testViewAll() {
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/store").headers(headers)
				.contentType(MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mvc.perform(requestBuilder).andReturn();
			assertEquals(200, result.getResponse().getStatus());
			assertEquals("application/json;charset=UTF-8", result.getResponse().getContentType());

		} catch (Exception e) {

			e.printStackTrace();
		}
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

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/store").headers(headers)
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

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/store/39").headers(headers)
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
	
		String storeJson = "{\n" + 
				"   \"merchant_id\":32,\n" + 
				"   \"name\":\"Protein++\",\n" + 
				"   \"description\":\"Suppliments shop\",\n" + 
				"   \"postalCode\":\"110032\",\n" + 
				"   \"address\":\"Kanpur\",\n" + 
				"   \"phone\":\"758768222\",\n" + 
				"   \"latitude\":\"26.70\",\n" + 
				"   \"longitude\":\"82.30\",\n" + 
				"   \"openingHours\":\"9\",\n" + 
				"   \"paymentMethodId\":[{\n" + 
				"   	\"id\":43,\n" + 
				"   	\"code\":101,\n" + 
				"   	\"name\":\"Cash\"\n" + 
				"   },{\n" + 
				"   	\"id\":48,\n" + 
				"   	\"code\":102,\n" + 
				"   	\"name\":\"Visa Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":56,\n" + 
				"   	\"code\":103,\n" + 
				"   	\"name\":\"Master Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":59,\n" + 
				"   	\"code\":106,\n" + 
				"   	\"name\":\"Paytm\"\n" + 
				"   }]" + 
				"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/store").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(storeJson).contentType(MediaType.APPLICATION_JSON);
		
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
	public void testCreateBadJson() {
		
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("2352b091-4994-4597-9e40-c17fe93705ef");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jedis.get("users::1")).thenReturn(user.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJra2tAZ21haWwuY29tIiwicGFzc3dvcmQiOiIyMzUyYjA5MS00OTk0LTQ1OTctOWU0MC1jMTdmZTkzNzA1ZWYifQ.9fYrMUB3NYlbRfn2qOlHd1B7zbYoZdF9y70Ua8HLY1qY-qwSVCDyCCSQ0sAvkiNZAVSxHgaTGBDngEULbs7h_A");
	
		String storeJson = "{\n" + 
				"   \"merchant_id\":32,\n" + 
				"   \"name\":\"Protein++\",\n" + 
				"   \"description\":\"Suppliments shop\",\n" + 
				"   \"postalCode\":\"110032\",\n" + 
				"   \"address\":\"Kanpur\",\n" + 
				"   \"phone\":\"758768222\",\n" + 
				"   \"latitude\":\"26.70\",\n" + 
				"   \"longitude\":\"82.30\",\n" + 
				"   \"openingHours\":\"9\",,\n" + 
				"   \"paymentMethodId\":[{\n" + 
				"   	\"id\":43,\n" + 
				"   	\"code\":101,\n" + 
				"   	\"name\":\"Cash\"\n" + 
				"   },{\n" + 
				"   	\"id\":48,\n" + 
				"   	\"code\":102,\n" + 
				"   	\"name\":\"Visa Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":56,\n" + 
				"   	\"code\":103,\n" + 
				"   	\"name\":\"Master Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":59,\n" + 
				"   	\"code\":106,\n" + 
				"   	\"name\":\"Paytm,\"\n" + 
				"   }]" + 
				"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/store").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(storeJson).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(400, result.getResponse().getStatus());
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

		String storeJson = "{ }";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/store").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(storeJson).contentType(MediaType.APPLICATION_JSON);

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
		
		String storeJson =  "{\n" + 
				"   \"merchant_id\":32,\n" + 
				"   \"name\":Protein++,\n" + 
				"   \"description\":7753465834,\n" + 
				"   \"postalCode\":\"110032\",\n" + 
				"   \"address\":,\n" + 
				"   \"phone\":\"758768222\",\n" + 
				"   \"latitude\":,\n" + 
				"   \"longitude\":,\n" + 
				"   \"openingHours\":\"9\",\n" + 
				"   \"paymentMethodId\":[{\n" + 
				"   	\"id\":43,\n" + 
				"   	\"code\":101,\n" + 
				"   	\"name\":\"Cash\"\n" + 
				"   },{\n" + 
				"   	\"id\":48,\n" + 
				"   	\"code\":102,\n" + 
				"   	\"name\":\"Visa Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":56,\n" + 
				"   	\"code\":103,\n" + 
				"   	\"name\":\"Master Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":59,\n" + 
				"   	\"code\":106,\n" + 
				"   	\"name\":\"Paytm\"\n" + 
				"   }]" + 
				"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/store").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(storeJson).contentType(MediaType.APPLICATION_JSON);
		
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
		
		String storeJson = "{\n" + 
				"   \"merchant_id\":null,\n" + 
				"   \"name\":null,\n" + 
				"   \"description\":null,\n" + 
				"   \"postalCode\":null,\n" + 
				"   \"address\":\"Kanpur\",\n" + 
				"   \"phone\":null,\n" + 
				"   \"latitude\":\"26.70\",\n" + 
				"   \"longitude\":\"82.30\",\n" + 
				"   \"openingHours\":\"9\",\n" + 
				"   \"paymentMethodId\":[{\n" + 
				"   	\"id\":null,\n" + 
				"   	\"code\":101,\n" + 
				"   	\"name\":\"Cash\"\n" + 
				"   },{\n" + 
				"   	\"id\":null,\n" + 
				"   	\"code\":102,\n" + 
				"   	\"name\":\"Visa Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":null,\n" + 
				"   	\"code\":103,\n" + 
				"   	\"name\":\"Master Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":59,\n" + 
				"   	\"code\":106,\n" + 
				"   	\"name\":\"Paytm\"\n" + 
				"   }]" + 
				"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/store").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(storeJson).contentType(MediaType.APPLICATION_JSON);
		
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

		String storeJson = "{\n" + 
				"   \"merchant_id\":32,\n" + 
				"   \"name\":\"Protein++\",\n" + 
				"   \"description\":\"Suppliments shop\",\n" + 
				"   \"postalCode\":\"110032\",\n" + 
				"   \"address\":\"Kanpur\",\n" + 
				"   \"phone\":\"758768222\",\n" + 
				"   \"latitude\":\"26.70\",\n" + 
				"   \"longitude\":\"82.30\",\n" + 
				"   \"openingHours\":\"9\",\n" + 
				"   \"paymentMethodId\":[{\n" + 
				"   	\"id\":43,\n" + 
				"   	\"code\":101,\n" + 
				"   	\"name\":\"Cash\"\n" + 
				"   },{\n" + 
				"   	\"id\":48,\n" + 
				"   	\"code\":102,\n" + 
				"   	\"name\":\"Visa Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":56,\n" + 
				"   	\"code\":103,\n" + 
				"   	\"name\":\"Master Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":59,\n" + 
				"   	\"code\":106,\n" + 
				"   	\"name\":\"Paytm\"\n" + 
				"   }]" + 
				"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/store/39").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(storeJson).contentType(MediaType.APPLICATION_JSON);

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

		String storeJson =  "{\n" + 
				"   \"merchant_id\":32,\n" + 
				"   \"name\":\"Protein++\",\n" + 
				"   \"description\":\"Suppliments shop\",\n" + 
				"   \"postalCode\":\"110032\",\n" + 
				"   \"address\":\"Kanpur\",\n" + 
				"   \"phone\":\"758768222\",\n" + 
				"   \"latitude\":\"26.70\",\n" + 
				"   \"longitude\":\"82.30\",\n" + 
				"   \"openingHours\":\"9\",\n" + 
				"   \"paymentMethodId\":[{\n" + 
				"   	\"id\":43,\n" + 
				"   	\"code\":101,\n" + 
				"   	\"name\":\"Cash\"\n" + 
				"   },{\n" + 
				"   	\"id\":48,\n" + 
				"   	\"code\":102,\n" + 
				"   	\"name\":\"Visa Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":56,\n" + 
				"   	\"code\":103,\n" + 
				"   	\"name\":\"Master Card\"\n" + 
				"   },{\n" + 
				"   	\"id\":59,\n" + 
				"   	\"code\":106,\n" + 
				"   	\"name\":\"Paytm\"\n" + 
				"   }]" + 
				"}";
		;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/store/").accept(MediaType.APPLICATION_JSON)
				.content(storeJson).contentType(MediaType.APPLICATION_JSON).headers(headers);

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

		String storeJson = "{ }";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/store/39").headers(headers).accept(MediaType.APPLICATION_JSON)
				.content(storeJson).contentType(MediaType.APPLICATION_JSON);

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

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/store/39").headers(headers);
		
		MvcResult result;
		try {
			result = mvc.perform(requestBuilder).andReturn();
			assertEquals(200, result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
