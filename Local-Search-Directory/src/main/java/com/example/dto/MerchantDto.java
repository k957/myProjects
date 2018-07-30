package com.example.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;



public class MerchantDto {

	@NotNull(message="name cannot be null")
	private String name;
	@NotNull(message="display_name cannot be null")
	private String displayName;//username
	@Email(message="mail id should be in proper format")
	private String mailId;
	@Column(name = "status", nullable = false, columnDefinition = "char default A")
	private char status;
	@NotNull(message="mobile no. cannot be null")
	private String mobileNo;
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	private List<FeedDto> feedDtoList;
	private List<ProductDto> products;
	private List<StoreDto> stores;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}


	public List<FeedDto> getFeedDtoList() {
		return feedDtoList;
	}
	public void setFeedDtoList(List<FeedDto> feedDtoList) {
		this.feedDtoList = feedDtoList;
	}
	public List<ProductDto> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}
	public List<StoreDto> getStores() {
		return stores;
	}
	public void setStores(List<StoreDto> stores) {
		this.stores = stores;
	}
	
	
}
