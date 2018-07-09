package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.repository.IUserRepository;
import com.example.security.SecurityAuthenticationTokenFilter;
import com.example.service.IUserService;

@RestController
@RequestMapping("/Logout")
public class LogoutController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private SecurityAuthenticationTokenFilter securityAuthenticationTokenFilter;

	@CacheEvict(value = "users", allEntries = true)
	@DeleteMapping()
	public Map<String, Object> logout(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String token = securityAuthenticationTokenFilter.getToken(request);
			User user = userRepository.findByToken(token);
			System.out.println(user);
			user.setToken(null);
			userService.save(user);
			map.put("status", HttpStatus.OK);
			map.put("Message", "Successfully logout");
		} catch (Exception e) {

		}
		return map;
	}
}
