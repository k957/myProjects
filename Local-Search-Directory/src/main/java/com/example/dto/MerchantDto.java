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
	private String display_name;//username
	@Email(message="mail id should be in proper format")
	private String mail_id;
	@Column(name = "status", nullable = false, columnDefinition = "char default A")
	private char status;
	private Date created_at;
	@NotNull(message="mobile no. cannot be null")
	private String mobile_no;
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
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
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getMail_id() {
		return mail_id;
	}
	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
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
