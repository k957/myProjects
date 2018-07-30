package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.security.JwtGenerator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/LocalSearchDirectory/token")
@Api(value="Token Controller REST Endpoint",description="Token Generating API")
public class TokenController {
	private JwtGenerator jwtGenerator;

	public TokenController(JwtGenerator jwtGenerator) {
		this.jwtGenerator = jwtGenerator;
	}

	@PostMapping
	@ApiOperation(value="generates token for Merchant")
	public String generate(@RequestBody final User user) {

		return jwtGenerator.generate(user);

	}
}
