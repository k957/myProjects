package com.example.dto;


public class PaymentMethodDto {
	
	private Long storeId=null;
	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
