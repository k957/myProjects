package com.example.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ProductDto {

	@NotNull(message = "merchant id cannot be null")
	private Long merchantId;

	private List<FeedDto> feedsDto;
	@NotNull(message = "name cannot be null")
	private String name;
	private String description;

	private String color = null;
	private String size = null;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
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
