package com.example.model;

import java.util.Date;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class PaymentMethod {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToMany(mappedBy = "paymentMethods")
	private List<Store> stores;

	private String code;
	private String name;
	@Column(name="created_at")
	private Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

	public String getCode() {
		return code;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public PaymentMethod(Long id, List<Store> stores, String code, String name, Date createdAt) {
		super();
		this.id = id;
		this.stores = stores;
		this.code = code;
		this.name = name;
		this.createdAt = createdAt;
	}

	public PaymentMethod() {
		super();

	}

	@Override
	public String toString() {
		return "PaymentMethod [id=" + id + ", stores=" + stores + ", code=" + code + ", name=" + name + ", created_at="
				+ createdAt + "]";
	}

}
