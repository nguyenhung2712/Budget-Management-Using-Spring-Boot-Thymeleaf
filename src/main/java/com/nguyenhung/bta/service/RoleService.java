package com.nguyenhung.bta.service;

import java.util.List;

import com.nguyenhung.bta.entity.Role;

public interface RoleService {
	List<Role> getAllRoles();
	Role saveRole(Role role);
	void removeRole(Long id);
	Role getByName(String name);
}
