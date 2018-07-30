package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public User loginUser(String username, String password) {
		System.out.println(password+"=======hh======="+username);
		User user = userRepository.findByUsernameAndPassword(username, password);
		System.out.println(user+"====================user");
		return user;
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

}
