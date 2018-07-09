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

import com.example.dto.StoreDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Store;
import com.example.repository.IStoreRepository;
import com.example.service.IStoreService;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/v1/store")
public class StoreController {

	@Autowired
	private IStoreRepository storeRepository;

	@Autowired
	private IStoreService storeService;

	String err = "JWT Token is missing";
	Jedis jedis = new Jedis("localhost");

	@GetMapping
	public ResponseEntity<?> viewAll() {
		if (jedis.get("users::1") != null) {
			List<Store> store = storeService.viewAll();
			HttpHeaders responseHeaders = new HttpHeaders();
			return new ResponseEntity<>(store, responseHeaders, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> viewOne(@PathVariable(value = "id") Long id) {
		if (jedis.get("users::1") != null) {
			Store store = storeService.viewOne(id);
			HttpHeaders responseHeaders = new HttpHeaders();
			return new ResponseEntity<>(store, responseHeaders, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody StoreDto storeDto) {
		if (jedis.get("users::1") != null) {
			
			Store store = storeService.create(storeDto);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(store, responseHeader, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody StoreDto storeDto, @PathVariable(value = "id") Long id) {
		if (jedis.get("users::1") != null) {
			Store store = storeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Store", "StoreID", id));
			store.setCreated_at(new Date());
			store.setAddress(storeDto.getAddress());
			store.setDescription(storeDto.getDescription());
			store.setOpening_hours(storeDto.getOpening_hours());
			store.setPhone(storeDto.getPhone());
			store.setPostal_code(storeDto.getPostal_code());
			store.setLatitude(storeDto.getLatitude());
			store.setLongitude(storeDto.getLongitude());
			Store updatedStore = storeRepository.save(store);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(updatedStore, responseHeader, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		if (jedis.get("users::1") != null) {
			Store store = storeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Store", "Store_id", id));
			storeRepository.delete(store);
			return ResponseEntity.ok().build();
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}
}
