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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/v1/PaymentMethod")
@Api(value="Payment method controller REST Endpoint",description="Payment Method API")
public class PaymentMethodController {

	@Autowired
	private IPaymentMethodRepository paymentMethodRepository;

	@Autowired
	private IPaymentMethodService paymentMethodservice;

	String err = "JWT Token is missing";
	Jedis jedis = new Jedis("localhost");

	@GetMapping
	@ApiOperation(value="returns the list of all Payment Methods available",response=PaymentMethodDto.class)
	public ResponseEntity<?> viewAll() {
		if (jedis.get("users::1") != null) {
			List<PaymentMethod> paymentMethod = paymentMethodservice.viewAll();
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(paymentMethod, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/{id}")
	@ApiOperation(value="returns one Payment Method whose ID provided in the URL",response=PaymentMethodDto.class)
	public ResponseEntity<?> viewOne(@PathVariable("id") Long id) {
		if (jedis.get("users::1") != null) {
			PaymentMethod paymentMethod = paymentMethodservice.viewOne(id);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(paymentMethod, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping
	@ApiOperation(value="method to create Payment Methods",response=PaymentMethodDto.class)
	public ResponseEntity<?> create(@Valid @RequestBody PaymentMethodDto paymentMethodDto) {
		if (jedis.get("users::1") != null) {
			PaymentMethod paymentMethod = paymentMethodservice.create(paymentMethodDto);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(paymentMethod, responseHeader, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@PutMapping("/{id}")
	@ApiOperation(value="Updates Payment Method whose ID is provided in the URL",response=PaymentMethodDto.class)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody PaymentMethodDto paymentMethodDto) {
		if (jedis.get("users::1") != null) {
			PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("PaymentMethod", "PaymentMethod ID", id));
			paymentMethod.setCreatedAt(new Date());
			paymentMethod.setCode(paymentMethodDto.getCode());
			paymentMethod.setName(paymentMethodDto.getName());
			paymentMethodRepository.save(paymentMethod);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(paymentMethod, responseHeader, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@DeleteMapping("/{id}")
	@ApiOperation(value="Deletes Product whose ID is provided in the URL",response=PaymentMethodDto.class)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		if (jedis.get("users::1") != null) {
			PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("PaymentMethod", "PaymentMethod ID", id));
			paymentMethodRepository.delete(paymentMethod);
			return ResponseEntity.ok().build();
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}
}
