package com.example.dto;

import java.util.List;

import com.example.model.PaymentMethod;

public class StoreDto {
	private List<PaymentMethod> paymentMethodId;

	private Long merchant_id;

	private List<FeedDto> feedsDto;

	private String name;
	private String description;
	private String postal_code;
	private String address;
	private String phone;
	private double latitude;
	private double longitude;
	private String opening_hours;

	public List<PaymentMethod> getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(List<PaymentMethod> paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

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

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getOpening_hours() {
		return opening_hours;
	}

	public void setOpening_hours(String opening_hours) {
		this.opening_hours = opening_hours;
	}

}
