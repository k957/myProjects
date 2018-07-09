package com.example.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.PaymentMethodDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.PaymentMethod;
import com.example.repository.IPaymentMethodRepository;
import com.example.service.IPaymentMethodService;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/v1/PaymentMethod")
public class PaymentMethodController {

	@Autowired
	private IPaymentMethodRepository paymentMethodRepository;

	@Autowired
	private IPaymentMethodService paymentMethodservice;

	String err = "JWT Token is missing";
	Jedis jedis = new Jedis("localhost");

	@GetMapping
	public ResponseEntity<?> viewAll() {
		if (jedis.get("users::1") != null) {
			List<PaymentMethod> PaymentMethod = paymentMethodservice.viewAll();
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(PaymentMethod, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> viewOne(@PathVariable("id") Long id) {
		if (jedis.get("users::1") != null) {
			PaymentMethod PaymentMethod = paymentMethodservice.viewOne(id);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(PaymentMethod, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody PaymentMethodDto PaymentMethodDto) {
		if (jedis.get("users::1") != null) {
			PaymentMethod PaymentMethod = paymentMethodservice.create(PaymentMethodDto);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(PaymentMethod, responseHeader, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody PaymentMethodDto PaymentMethodDto) {
		if (jedis.get("users::1") != null) {
			PaymentMethod PaymentMethod = paymentMethodRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("PaymentMethod", "PaymentMethod ID", id));
			PaymentMethod.setCreated_at(new Date());
			PaymentMethod.setCode(PaymentMethodDto.getCode());
			PaymentMethod.setName(PaymentMethodDto.getName());
			paymentMethodRepository.save(PaymentMethod);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(PaymentMethod, responseHeader, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		if (jedis.get("users::1") != null) {
			PaymentMethod PaymentMethod = paymentMethodRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("PaymentMethod", "PaymentMethod ID", id));
			paymentMethodRepository.delete(PaymentMethod);
			return ResponseEntity.ok().build();
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}
}
