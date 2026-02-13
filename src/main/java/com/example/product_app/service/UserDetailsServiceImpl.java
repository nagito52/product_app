package com.example.product_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.product_app.entity.User;
import com.example.product_app.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("not found"));
		// DBのroleが "ADMIN" のとき Spring の .roles("ADMIN") で ROLE_ADMIN が付与される
		String role = user.getRole();
		if (role != null && role.startsWith("ROLE_")) {
			role = role.substring(5);
		}
		if (role == null || role.isBlank()) {
			role = "USER";
		}
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPasswordHash())
				.roles(role)
				.build();
	}
}
