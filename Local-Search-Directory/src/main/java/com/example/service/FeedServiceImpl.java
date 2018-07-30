
package com.example.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assembler.FeedAssembler;
import com.example.dto.FeedDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Feed;
import com.example.repository.IFeedRepository;

@Service
public class FeedServiceImpl implements IFeedService {
	@Autowired
	private IFeedRepository feedRepository;

	@Autowired
	FeedAssembler feedAssembler;

	@Override
	public List<Feed> viewAll() {
		List<Feed> feedList = feedRepository.findAll();
		return feedList;
	}

	@Override
	public Feed viewOne(Long id) {
		Feed feed = feedRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Feed", "Feed ID", id));
		return feed;
	}

	@Override
	public Feed update(FeedDto feedDto) {
		return null;
	}

	@Override
	public void delete(List<Feed> feedList) {
		//made in controller
	}

	@Override
	public Feed create(FeedDto feedDto) {
		Feed feed = feedAssembler.createFeedEntity(feedDto);
		feed.setCreatedAt(new Date());
		feedRepository.save(feed);
		return feed;
	}

}
