package org.analyser.dao;

import org.analyser.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long>{
	public AppRole findAppRoleById(Long id);
	public AppRole findAppRoleByRoleName(String roleName);
}
