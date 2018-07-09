package com.example.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ProductDto {

	@NotNull(message = "merchant id cannot be null")
	private Long merchant_id;

	private List<FeedDto> feedsDto;
	@NotNull(message = "name cannot be null")
	private String name;
	private String description;

	private String color = null;
	private String size = null;

	public Long getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(Long merchant_id) {
		this.merchant_id = merchant_id;
	}

	public List<FeedDto> getFeedsDto() {
		return feedsDto;
	}

	public void setFeedsDto(List<FeedDto> feedsDto) {
		this.feedsDto = feedsDto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
