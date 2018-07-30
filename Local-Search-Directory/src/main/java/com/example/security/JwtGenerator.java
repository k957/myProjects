package com.example.security;

import org.springframework.stereotype.Component;

import com.example.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {
	public String generate(User user) {
		String key="youtube";
		String base64EncodedKey = io.jsonwebtoken.impl.TextCodec.BASE64.encode(key);
		Claims claims = Jwts.claims().setSubject(user.getUsername());
		claims.put("password", user.getPassword());
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, base64EncodedKey).compact();
	}
}
