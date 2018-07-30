package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.repository.IUserRepository;
import com.example.security.SecurityAuthenticationTokenFilter;
import com.example.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/LocalSearchDirectory/Logout")
@Api(value="Logout Controller REST Endpoint",description="Merchant Logout API")
public class LogoutController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private SecurityAuthenticationTokenFilter securityAuthenticationTokenFilter;

	Jedis jedis = new Jedis("localhost");
	
	@DeleteMapping()
	@ApiOperation(value="Merchant Logout method")
	public Map<String, Object> logout(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String token = securityAuthenticationTokenFilter.getToken(request);
			User user = userRepository.findByToken(token);
			user.setToken(null);
			userService.save(user);
			jedis.del(token);
			map.put("status", HttpStatus.OK);
			map.put("Message", "Successfully logout");
		} catch (Exception e) {
			
		}
		return map;
	}
}
