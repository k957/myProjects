package com.example.service;

import com.example.model.User;

public interface IUserService {

	User loginUser(String username, String password);

	void save(User user);

}
