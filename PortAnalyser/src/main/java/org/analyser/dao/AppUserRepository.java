package org.analyser.dao;

import org.analyser.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	public AppUser findAppUserById(Long id);
	public AppUser findAppUserByUsername(String username);
}
