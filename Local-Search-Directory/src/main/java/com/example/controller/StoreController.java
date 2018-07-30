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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/v1/store")
@Api(value="Store Controller REST Endpoint",description="Store Controller API")
public class StoreController {

	@Autowired
	private IStoreRepository storeRepository;

	@Autowired
	private IStoreService storeService;

	String err = "JWT Token is missing";
	Jedis jedis = new Jedis("localhost");

	@GetMapping
	@ApiOperation(value="returns the list of all Stores available",response=StoreDto.class)
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
	@ApiOperation(value="returns one Store whose ID provided in the URL",response=StoreDto.class)
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
	@ApiOperation(value="method to create Store",response=StoreDto.class)
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
	@ApiOperation(value="Updates Store whose ID is provided in the URL",response=StoreDto.class)
	public ResponseEntity<?> update(@Valid @RequestBody StoreDto storeDto, @PathVariable(value = "id") Long id) {
		if (jedis.get("users::1") != null) {
			Store store = storeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Store", "StoreID", id));
			store.setCreatedAt(new Date());
			store.setAddress(storeDto.getAddress());
			store.setDescription(storeDto.getDescription());
			store.setOpeningHours(storeDto.getOpeningHours());
			store.setPhone(storeDto.getPhone());
			store.setPostalCode(storeDto.getPostalCode());
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
	@ApiOperation(value="deletes Store whose ID is provided in the URL",response=StoreDto.class)
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
