package org.analyser.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name = "user", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"username"})})
public class AppUser implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Email
	@NotNull
	@Column(unique = true)
	private String username;
	private String password;
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private Person person;
	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<AppRole> roles = new ArrayList<>(); 
	@OneToMany(mappedBy = "user")
	private Collection<SessionInfo> sessionInfos = new ArrayList<>(); 
	@NotNull
	@CreatedDate
	@Column(name = "created_date")
	private Date createdDate = new Date();
	
	public AppUser() {
		super();
	}

	public AppUser(Long id, @Email @NotNull String username, String password, @NotNull Person person,
			@NotNull Collection<AppRole> roles, Collection<SessionInfo> sessionInfos, @NotNull Date createdDate) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.person = person;
		this.roles = roles;
		this.sessionInfos = sessionInfos;
		this.createdDate = createdDate;
	}

	public AppUser(@Email @NotNull String username, String password, @NotNull Person person,
			@NotNull Collection<AppRole> roles, Collection<SessionInfo> sessionInfos, @NotNull Date createdDate) {
		super();
		this.username = username;
		this.password = password;
		this.person = person;
		this.roles = roles;
		this.sessionInfos = sessionInfos;
		this.createdDate = createdDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * Deserialisation ignoree. Parce que JPARepository<AppUser, Long> a besoin de getter et setter de AppUser.class pour save() et find(). 
	 * Du coup, on ne peut pas faire un getPassword() comme par exemple dans le cas de "user = JPARepository.findByUsername(username)". 
	 * Du coup, avec l'objet "user", on ne peut pas acceder a l'attribut "password" avec getPassword().
	 * Quand on essait de recuperer les utilisateurs(objet JSON) via REST service, l'attribut password est absent.
	 * Exemple d'un corps d'une reponse http: {"id" : 1, "username": "xyz", "roles": ["ADMIN"]}
	 * @return password
	 */
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	/**
	 * Quand on fait une requette hhtp POST d'un utilisateur ex: {"username": "xyz", "password": "1234"}, en objet JSON, 
	 * JPARepository.save(user) utilise le setter de AppUser pour serialiser cet objet dans la base de donnees
	 * @param password
	 */
	@JsonSetter
	public void setPassword(String password) {
		this.password = password;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Collection<AppRole> getRoles() {
		return roles;
	}

	public void setRoles(Collection<AppRole> roles) {
		this.roles = roles;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Collection<SessionInfo> getSessionInfos() {
		return sessionInfos;
	}

	public void setSessionInfos(Collection<SessionInfo> sessionInfos) {
		this.sessionInfos = sessionInfos;
	}
}
