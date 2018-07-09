package com.example.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "merchant_id")
	private Merchant merchant;

	private String username;
	private String password;
	private Date lastLogin;
	private String token;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User(Long userId, Merchant merchant, String username, String password, Date lastLogin, String token) {
		super();
		this.userId = userId;
		this.merchant = merchant;
		this.username = username;
		this.password = password;
		this.lastLogin = lastLogin;
		this.token = token;
	}

	public User() {
		super();

	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", merchant=" + merchant + ", username=" + username + ", password=" + password
				+ ", lastLogin=" + lastLogin + ", token=" + token + "]";
	}

}
