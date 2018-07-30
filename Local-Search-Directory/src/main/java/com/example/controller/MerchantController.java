package com.example.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MerchantDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Merchant;
import com.example.repository.IMerchantRepository;
import com.example.service.IMerchantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/v1/merchant")
@Api(value="Merchant Controller REST Endpoint",description="Merchant Info and update API")
public class MerchantController {

	@Autowired
	private IMerchantService merchantService;

	@Autowired
	private IMerchantRepository merchantRepository;

	String err = "JWT Token is missing";
	Jedis jedis = new Jedis("localhost");
	@ApiOperation(value="Returns the list of all merchants registered",response=MerchantDto.class)
	@GetMapping
	public ResponseEntity<?> viewAll() {
		if (jedis.get("users::1") != null) {
			List<Merchant> merchant = merchantService.viewAll();
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(merchant, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value="Returns one Merchant details whose ID is provided in the URL",response=MerchantDto.class)
	@GetMapping("/{id}")
	public ResponseEntity<?> viewOne(@PathVariable(value = "id") Long id) {
		if (jedis.get("users::1") != null) {
			Merchant merchant = merchantRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Merchant", "id", id));
			HttpHeaders responseHeaders = new HttpHeaders();
			return new ResponseEntity<>(merchant, responseHeaders, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value="Updates Merchant whose ID is provided in the URL",response=MerchantDto.class)
	@PutMapping(path="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody MerchantDto merchantDto) {
		if (jedis.get("users::1") != null) {
			Merchant merchant = merchantRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Merchant", "id", id));

			merchant.setCreatedAt(new Date());
			merchant.setDisplayName(merchantDto.getDisplayName());

			merchant.setName(merchantDto.getName());
			merchant.setStatus(merchantDto.getStatus());
			merchant.setMobileNo(merchantDto.getMobileNo());

			Merchant updatedMerchant = merchantRepository.save(merchant);
			HttpHeaders responseHeaders = new HttpHeaders();
			return new ResponseEntity<>(updatedMerchant, responseHeaders, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}

}
