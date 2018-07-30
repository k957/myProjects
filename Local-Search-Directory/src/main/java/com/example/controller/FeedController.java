package com.example.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/v1/feed")
@Api(value="Feed Controller REST Endpoint",description="Feed Controller API")
public class FeedController {
	@Autowired
	private IFeedRepository feedRepository;

	@Autowired
	private IFeedService feedService;

	String err = "JWT Token is missing";
	Jedis jedis = new Jedis("localhost");

	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="returns the list of all feeds",response=FeedDto.class)
	public ResponseEntity<?> viewAll() {
		if (jedis.get("users::1") != null) {
			List<Feed> feed = feedService.viewAll();
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(feed, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/{id}")
	@ApiOperation(value="returns one feed whose ID provided in the URL",response=FeedDto.class)
	public ResponseEntity<?> viewOone(@PathVariable("id") Long id) {
		if (jedis.get("users::1") != null) {
			Feed feed = feedService.viewOne(id);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(feed, responseHeader, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping
	@ApiOperation(value="method to create feeds",response=FeedDto.class)
	public ResponseEntity<?> create(@Valid @RequestBody FeedDto feedDto) {
		if (jedis.get("users::1") != null) {
			Feed feed = feedService.create(feedDto);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(feed, responseHeader, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@PutMapping("/{id}")
	@ApiOperation(value="Updates Feed whose ID is provided in the URL",response=FeedDto.class)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody FeedDto feedDto) {
		if (jedis.get("users::1") != null) {
			Feed feed = feedRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Feed", "Feed ID", id));
			feed.setCreatedAt(new Date());
			feed.setEndDate(feedDto.getEndDate());
			feed.setPrice(feedDto.getPrice());
			feed.setQuantity(feedDto.getQuantity());
			feed.setSalePrice(feedDto.getSalePrice());
			feed.setStartDate(feedDto.getStartDate());
			feedRepository.save(feed);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(feed, responseHeader, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}

	@DeleteMapping("/{id}")
	@ApiOperation(value="Deletes Feed whose ID is provided in the URL",response=FeedDto.class)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		if (jedis.get("users::1") != null) {
			Feed feed = feedRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Feed", "Feed ID", id));
			feedRepository.delete(feed);
			return ResponseEntity.ok().build();
		} else {
			return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		}

	}
}
