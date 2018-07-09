package com.example.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.repository.IUserRepository;
import com.example.security.JwtGenerator;
import com.example.service.IUserService;

@RestController
@RequestMapping("/LocalSearchDirctory/login")
public class LoginController {
	@Autowired
	private JwtGenerator jwtGenerator;

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserRepository userRepository;

	public LoginController(JwtGenerator jwtGenerator) {
		super();
		this.jwtGenerator = jwtGenerator;
	}

	@Cacheable(value = "users", key = "1")
	@PostMapping
	public Map<String, String> login(@RequestBody final User jwtUser, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			User user = userService.loginUser(jwtUser.getUsername(), jwtUser.getPassword());
			if (user != null) {
				String token = jwtGenerator.generate(user);
				user.setToken(token);
				user.setLastLogin(new Date());
				userRepository.save(user);
				session.setAttribute("username", user.getUsername());
				map.put("Token", token);
				map.put("Status", HttpStatus.CREATED.toString());
			} else {
				map.put("Status", HttpStatus.UNAUTHORIZED.toString());
				map.put("Error Message", "Please check your credentials");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
		}
		return map;
	}
}
