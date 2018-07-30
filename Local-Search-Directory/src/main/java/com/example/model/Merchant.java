package com.example.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Merchant {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@Column(unique = true,name="display_name")
	private String displayName;
	@Column(name="mail_id")
	private String mailId;
	private char status;
	@Column(name="created_at")
	private Date createdAt;
	@Column(name="mobile_no")
	private String mobileNo;
	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
	private List<Feed> feeds;

	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
	private List<Product> products;

	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
	private List<Store> stores;

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<Feed> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<Feed> feeds) {
		this.feeds = feeds;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

	public Merchant(Long id, String name, String displayName, String mailId, char status, Date createdAt,
			String mobileNo, List<Feed> feeds, List<Product> products, List<Store> stores) {
		super();
		this.id = id;
		this.name = name;
		this.displayName = displayName;
		this.mailId = mailId;
		this.status = status;
		this.createdAt = createdAt;
		this.mobileNo = mobileNo;
		this.feeds = feeds;
		this.products = products;
		this.stores = stores;
	}

	public Merchant() {
		super();

	}

}
