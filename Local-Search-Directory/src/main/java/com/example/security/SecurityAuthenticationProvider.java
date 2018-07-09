package com.example.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.model.SecurityAuthenticationToken;
import com.example.model.User;
import com.example.model.SecurityUserDetails;

@Component
public class SecurityAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private SecurityValidator validator;

	@Override
	public boolean supports(Class<?> aClass) {
		return SecurityAuthenticationToken.class.isAssignableFrom(aClass);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0, UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
		SecurityAuthenticationToken securityAuthenticationToken = (SecurityAuthenticationToken) usernamePasswordAuthenticationToken;
		String token = securityAuthenticationToken.getToken();

		User user = null;
		SecurityUserDetails userdetails = null;
		user = validator.validate(token);

		if (user == null) {
			throw new RuntimeException("JWT token is incorrect");
		}
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(user.getUsername());

		userdetails = new SecurityUserDetails(user.getUsername(), user.getPassword(), token, grantedAuthorities);
		return userdetails;
	}

}
