package com.nguyenhung.bta.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenhung.bta.entity.User;
import com.nguyenhung.bta.repository.UserRepository;
import com.nguyenhung.bta.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public List<User> getAllUsers() {
		return this.userRepo.findAll();
	}

	@Override
	public User saveUser(User user) {
		return this.userRepo.save(user);
	}

	@Override
	public void removeUser(Long id) {
		this.userRepo.deleteById(id);
	}

	@Override
	public User getUserById(Long id) {
		Optional<User> optional = this.userRepo.findById(id);
		User user = null;
		if(optional.isPresent()) {
			user = optional.get();
		} else {
			throw new RuntimeException("User not found for id: " + id);
		}
		return user;
	}

	@Override
	public User getUserByUsername(String username) {
		return this.userRepo.findByUsername(username).get();
	}

	@Override
	public int setUsername(String username, Long id) {
		return this.userRepo.setUsername(username, id);
	}

	@Override
	public int setPassword(String password, Long id) {
		return this.userRepo.setPassword(password, id);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return this.userRepo.existsByEmail(email);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return this.userRepo.existsByUsername(username);
	}

}
