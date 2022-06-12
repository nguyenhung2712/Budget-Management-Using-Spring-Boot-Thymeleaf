package com.nguyenhung.bta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenhung.bta.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE users SET username = ?1 WHERE user_id = ?2", nativeQuery = true)
	int setUsername(String username, Long id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE users SET password = ?1 WHERE user_id = ?2", nativeQuery = true)
	int setPassword(String password, Long id);
}
