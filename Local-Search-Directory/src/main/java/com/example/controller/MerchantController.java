package com.example.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/v1/merchant")
public class MerchantController {

	@Autowired
	private IMerchantService merchantService;

	@Autowired
	private IMerchantRepository merchantRepository;

	String err = "JWT Token is missing";
	Jedis jedis = new Jedis("localhost");

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

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody MerchantDto merchantDto) {
		if (jedis.get("users::1") != null) {
			Merchant merchant = merchantRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Merchant", "id", id));

			merchant.setCreated_at(new Date());
			merchant.setDisplay_name(merchantDto.getDisplay_name());

			merchant.setName(merchantDto.getName());
			merchant.setStatus(merchantDto.getStatus());
			merchant.setMobile_no(merchantDto.getMobile_no());

			Merchant updatedMerchant = merchantRepository.save(merchant);
			HttpHeaders responseHeaders = new HttpHeaders();
			return new ResponseEntity<>(updatedMerchant, responseHeaders, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}

}
