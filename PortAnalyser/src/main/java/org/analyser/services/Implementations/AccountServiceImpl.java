package org.analyser.services.Implementations;

import java.util.Date;
import java.util.List;

import org.analyser.dao.AppRoleRepository;
import org.analyser.dao.AppUserRepository;
import org.analyser.dao.PersonRepository;
import org.analyser.dao.SessionInfoRepository;
import org.analyser.entities.AppRole;
import org.analyser.entities.AppUser;
import org.analyser.entities.Person;
import org.analyser.entities.SessionInfo;
import org.analyser.services.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService{
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private SessionInfoRepository sessionInfoRepository;
	
	@Override
	public AppUser saveUser(AppUser appUser) {
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		if(appUser.getUsername() == null){
			appUser.setUsername(appUser.getPerson().getEmail());
		}
		appUser.setPerson(personRepository.save(appUser.getPerson()));
		return appUserRepository.save(appUser);
	}

	@Override
	public AppUser findUserByUsername(String username) {
		return appUserRepository.findAppUserByUsername(username);
	}

	@Override
	public AppRole saveRole(AppRole appRole) {
		return appRoleRepository.save(appRole);
	}

	@Override
	public AppRole updateRole(AppRole appRole) {
		return appRoleRepository.save(appRole);
	}

	@Override
	public List<AppRole> getRoles() {
		return appRoleRepository.findAll();
	}

	@Override
	public AppRole findRoleByRoleName(String roleName) {
		return appRoleRepository.findAppRoleByRoleName(roleName);
	}

	@Override
	public AppRole findRoleById(Long id) {
		return appRoleRepository.findAppRoleById(id);
	}

	@Override
	public AppUser addRoleToUser(String username, String roleName) {
		AppRole appRole = appRoleRepository.findAppRoleByRoleName(roleName);
		AppUser appUser = appUserRepository.findAppUserByUsername(username);
		appUser.getRoles().add(appRole);
		return appUser;
	}

	@Override
	public AppUser addPersonToUser(Long id, String username) {
		Person person = personRepository.findPersonById(id);
		AppUser appUser = appUserRepository.findAppUserByUsername(username);
		appUser.setPerson(person);
		return appUser;
	}

	@Override
	public AppUser findUserById(Long id) {
		return appUserRepository.findAppUserById(id);
	}

	@Override
	public SessionInfo saveSessionInfo(SessionInfo sessionInfo) {
		return sessionInfoRepository.save(sessionInfo);
	}

	@Override
	public List<AppUser> findAllUsers() {
		return appUserRepository.findAll();
	}

	@Override
	public List<Person> findAllPersons() {
		return personRepository.findAll();
	}

	@Override
	public Date findLastSession(Long id) {
		return sessionInfoRepository.findLastSession(id);
	}

	@Override
	public List<SessionInfo> findAllSessionInfo(AppUser user) {
		return sessionInfoRepository.findByUserOrderByCreatedDateDesc(user);
	}

	@Override
	public List<AppRole> findAllRoles() {
		return appRoleRepository.findAll();
	}

	@Override
	public void deleteUser(Long id) {
		appUserRepository.deleteById(id);
	}
}
