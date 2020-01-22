package org.analyser.services.interfaces;

import java.util.Date;
import java.util.List;

import org.analyser.entities.AppRole;
import org.analyser.entities.AppUser;
import org.analyser.entities.Person;
import org.analyser.entities.SessionInfo;

public interface IAccountService {
	public AppUser saveUser(AppUser user);
	public List<AppUser> findAllUsers();
	public AppUser findUserByUsername(String username);
	public AppUser findUserById(Long id);
	public void deleteUser(Long id);
	public AppRole saveRole(AppRole role);
	public List<AppRole> findAllRoles();
	public AppRole updateRole(AppRole role);
	public List<AppRole> getRoles();
	public AppRole findRoleByRoleName(String roleName);
	public AppRole findRoleById(Long id);
	public AppUser addRoleToUser(String username, String roleName);
	public AppUser addPersonToUser(Long id, String username);
	public SessionInfo saveSessionInfo(SessionInfo sessionInfo);
	public List<SessionInfo> findAllSessionInfo(AppUser user);
	public Date findLastSession(Long id);
	public List<Person> findAllPersons();
}
