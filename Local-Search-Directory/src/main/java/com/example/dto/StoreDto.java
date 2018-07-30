package com.example.dto;

import java.util.List;

import com.example.model.PaymentMethod;

public class StoreDto {
	private List<PaymentMethod> paymentMethodId;

	private Long merchantId;

	private List<FeedDto> feedsDto;

	private String name;
	private String description;
	private String postalCode;
	private String address;
	private String phone;
	private double latitude;
	private double longitude;
	private String openingHours;

	public List<PaymentMethod> getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(List<PaymentMethod> paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

}
