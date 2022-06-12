package com.nguyenhung.bta.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenhung.bta.entity.Role;
import com.nguyenhung.bta.repository.RoleRepository;
import com.nguyenhung.bta.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public List<Role> getAllRoles() {
		return this.roleRepo.findAll();
	}

	@Override
	public Role saveRole(Role role) {
		return this.roleRepo.save(role);
	}

	@Override
	public void removeRole(Long id) {
		this.roleRepo.deleteById(id);
	}

	@Override
	public Role getByName(String name) {
		Optional<Role> optional = this.roleRepo.findByName(name);
		Role role = null;
		if (optional.isPresent()) {
			role = optional.get();
		} else {
			throw new RuntimeException("Role not found for name: " + name);
		}
		return role;
	}
	
}
