package org.analyser.security;

import java.util.ArrayList;
import java.util.Collection;

import org.analyser.entities.AppUser;
import org.analyser.services.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private IAccountService accountService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = accountService.findUserByUsername(username);
		if(user == null) throw new UsernameNotFoundException(username);
		
		/**
		 *  Collection de roleName. Les roles definissent les autorites d'un utilisateur
		 */
		Collection<GrantedAuthority> authorities = new ArrayList<>();  
		
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			System.out.println("Roles parcourus" +role.getRoleName());
		});
		
		/**
		 * Le constructeur de cette Classe User
		 * User: une classe de Spring
		 * 
		 * public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
				this(username, password, true, true, true, true, authorities);
			}
		 */
		 return new User(user.getUsername(), user.getPassword(), authorities);
	}

}
