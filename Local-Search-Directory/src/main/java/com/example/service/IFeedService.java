package com.example.service;

import java.util.List;

import com.example.dto.FeedDto;
import com.example.model.Feed;

public interface IFeedService {
	List<Feed> viewAll();
	Feed viewOne(Long id);
	Feed update(FeedDto FeedDto);
	void delete(List<Feed> FeedList);
	Feed create(FeedDto FeedDtoList);
}
