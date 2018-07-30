package com.rest.controller;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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
import com.example.controller.LoginController;
import com.example.model.User;
import com.example.security.JwtGenerator;
import com.example.service.IUserService;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {LocalSearchDirectoryApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginIntegrationTestCase {

	@Mock
	private IUserService userService;
	
	@LocalServerPort
	private int port;
	
	@Mock
	private JwtGenerator jwtGenerator;
	
	@InjectMocks
	private LoginController loginController;
	
	@Autowired
    private TestRestTemplate testRestTemplate;
	
	@Test
	public void testLogin() {
		String url = "http://localhost:" + port + "/LocalSearchDirectory/login";
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("qwerty");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);
		Mockito.when(jwtGenerator.generate(user)).thenReturn(ArgumentMatchers.anyString());
		
		 HttpHeaders headers = new HttpHeaders();
	     headers.add("Authorization", "Token ");
	     
	     HttpEntity<User> entity = new HttpEntity<>(user, headers);
	     
	     ResponseEntity<String> response = testRestTemplate.exchange(
	                url,
	                HttpMethod.POST, entity, String.class);
	     assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	public void testLoginFail() {
		String url = "http://localhost:" + port + "/LocalSearchDirectory/login";
		User user = new User();
		user.setUsername("kkk@gmail.com");
		user.setPassword("qwerty");
		Mockito.when(userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(null);
		//Mockito.when(jwtGenerator.generate(user)).thenReturn(null);
		HttpHeaders headers = new HttpHeaders();
	     headers.add("Authorization", "Token ");
	     
	     HttpEntity<User> entity = new HttpEntity<>(user, headers);
	     
	     ResponseEntity<String> response = testRestTemplate.exchange(
	                url,
	                HttpMethod.POST, entity, String.class);
	     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
	}
}
