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
	@Column(unique = true)
	private String display_name;
	private String mail_id;
	private char status;
	private Date created_at;
	private String mobile_no;
	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
	private List<Feed> feeds;

	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
	private List<Product> products;

	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
	private List<Store> stores;

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
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

	public Merchant(Long id, String name, String display_name, String mail_id, char status, Date created_at,
			String mobile_no, List<Feed> feeds, List<Product> products, List<Store> stores) {
		super();
		this.id = id;
		this.name = name;
		this.display_name = display_name;
		this.mail_id = mail_id;
		this.status = status;
		this.created_at = created_at;
		this.mobile_no = mobile_no;
		this.feeds = feeds;
		this.products = products;
		this.stores = stores;
	}

	public Merchant() {
		super();

	}

}
