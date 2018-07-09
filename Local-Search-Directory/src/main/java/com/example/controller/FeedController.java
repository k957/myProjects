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

import com.example.dto.FeedDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Feed;
import com.example.repository.IFeedRepository;
import com.example.service.IFeedService;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/v1/feed")
public class FeedController {
	@Autowired
	private IFeedRepository feedRepository;

	@Autowired
	private IFeedService feedService;

	String err = "JWT Token is missing";
	Jedis jedis = new Jedis("localhost");

	@GetMapping
	public ResponseEntity<?> viewAll() {
		if (jedis.get("users::1") != null) {
			List<Feed> Feed = feedService.viewAll();
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(Feed, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> viewOone(@PathVariable("id") Long id) {
		if (jedis.get("users::1") != null) {
			Feed Feed = feedService.viewOne(id);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(Feed, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody FeedDto FeedDto) {
		if (jedis.get("users::1") != null) {
			Feed Feed = feedService.create(FeedDto);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(Feed, responseHeader, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody FeedDto FeedDto) {
		if (jedis.get("users::1") != null) {
			Feed Feed = feedRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Feed", "Feed ID", id));
			Feed.setCreated_at(new Date());
			Feed.setEnd_date(FeedDto.getEnd_date());
			Feed.setPrice(FeedDto.getPrice());
			Feed.setQuantity(FeedDto.getQuantity());
			Feed.setSale_price(FeedDto.getSale_price());
			Feed.setStart_date(FeedDto.getStart_date());
			feedRepository.save(Feed);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(Feed, responseHeader, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		if (jedis.get("users::1") != null) {
			Feed Feed = feedRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Feed", "Feed ID", id));
			feedRepository.delete(Feed);
			return ResponseEntity.ok().build();
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}
}
