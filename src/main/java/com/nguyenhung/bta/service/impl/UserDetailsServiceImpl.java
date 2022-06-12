package com.nguyenhung.bta.service.impl;

import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenhung.bta.entity.CustomUserDetails;
import com.nguyenhung.bta.entity.User;
import com.nguyenhung.bta.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByUsername(username).get();
		CustomUserDetails customUser = new CustomUserDetails(user);
		
		if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
		
		Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) customUser.getAuthorities();
		Hibernate.initialize(authorities);
		return new CustomUserDetails(user);
	}
}
