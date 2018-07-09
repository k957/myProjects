package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	User findByUsernameAndPassword(String username, String password);

	User findByToken(String token);

	User findByUsername(String username);
}
