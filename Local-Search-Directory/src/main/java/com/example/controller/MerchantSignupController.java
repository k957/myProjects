package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MerchantDto;
import com.example.service.IMerchantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/LocalSearchDirectory/SignUp")
@Api(value="Merchant SignUp REST Endpoint",description="Merchant SignUp API")
public class MerchantSignupController {

	@Autowired
	private IMerchantService merchantService;
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Merchant SignUp")
	public ResponseEntity<?> create(@Valid @RequestBody MerchantDto merchantDto){
		String password=merchantService.create(merchantDto);
		HttpHeaders responseHeader = new HttpHeaders();
		return new ResponseEntity<>("Your password is: "+password,responseHeader,HttpStatus.CREATED);
	}
}
