package com.example.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dto.FeedDto;
import com.example.model.Feed;
import com.example.model.Merchant;
import com.example.model.Product;
import com.example.model.Store;
import com.example.repository.IMerchantRepository;
import com.example.repository.IProductRepository;
import com.example.repository.IStoreRepository;

@Component
public class FeedAssembler {

	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private IStoreRepository storeRepository;

	@Autowired
	private IMerchantRepository merchantRepository;

	public List<Feed> createFeedEntity(List<FeedDto> feedDtoList) {
		List<Feed> feeds = new ArrayList<>();
		feedDtoList.forEach(feedDto -> {
			Feed feed = new Feed();
			Product product = productRepository.getOne(feedDto.getProductId());
			Store store = storeRepository.getOne(feedDto.getStoreId());
			Merchant merchant = merchantRepository.getOne(feedDto.getMerchantId());
			feed.setPrice(feedDto.getPrice());
			feed.setProduct(product);
			feed.setMerchant(merchant);
			feed.setStore(store);
			feed.setEndDate(feedDto.getEndDate());
			feed.setQuantity(feedDto.getQuantity());
			feed.setSalePrice(feedDto.getSalePrice());
			feed.setStartDate(feedDto.getStartDate());

			feeds.add(feed);
		});
		return feeds;

	}
	
	public Feed createFeedEntity(FeedDto feedDto) {
		Feed feed = new Feed();
		feed.setPrice(feedDto.getPrice());
		Product product = productRepository.getOne(feedDto.getProductId());
		Store store = storeRepository.getOne(feedDto.getStoreId());
		Merchant merchant = merchantRepository.getOne(feedDto.getMerchantId());
		feed.setProduct(product);
		feed.setMerchant(merchant);
		feed.setStore(store);
		feed.setEndDate(feedDto.getEndDate());
		feed.setQuantity(feedDto.getQuantity());
		feed.setSalePrice(feedDto.getSalePrice());
		feed.setStartDate(feedDto.getStartDate());
		return feed;
	}
}
