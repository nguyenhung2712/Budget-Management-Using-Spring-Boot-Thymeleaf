package com.nguyenhung.bta.service;

import java.util.List;

import com.nguyenhung.bta.entity.User;

public interface UserService {
	List<User> getAllUsers();
	User getUserById(Long id);
	User saveUser(User user);
	User getUserByUsername(String username);
	void removeUser(Long id);
	int setUsername(String username, Long id);
	int setPassword(String password, Long id);
	
	Boolean existsByEmail(String email);
	Boolean existsByUsername(String username);
}
