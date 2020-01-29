package org.analyser.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.analyser.entities.AppRole;

public class UserRegistrationForm implements Serializable{
	private Long id;	
	@NotBlank(message = "Veuilez saisir votre nom")
	@NotNull(message = "Ce champs ne peut pas être vide")
	private String lastname;
	@NotBlank(message = "Veuilez saisir votre prénom")
	@NotNull(message = "Ce champs ne peut pas être vide")
	private String firstname;
	@NotBlank(message = "Veuilez saisir votre email avec une adresse email valide")
	@NotNull(message = "Ce champs ne peut pas être vide")
	@Email
	private String email;
	@NotBlank(message = "Veuilez saisir votre adresse")
	@NotNull(message = "Ce champs ne peut pas être vide")
	private String address;
	@NotBlank(message = "Ce champs ne peut pas être vide")
	@Size(min = 10, max = 12, message = "Minimum 10 caractères et maximum 12 caractères")
	private String telephone;
	private Collection<AppRole> roles = new ArrayList<>(); 
	@NotBlank(message = "Veuilez saisir votre mot de passe")
	@NotNull(message = "Ce champs ne peut pas être vide")
	private String password;
	@NotBlank(message = "Veuilez resaisir votre mot de passe")
	@NotNull(message = "Ce champs ne peut pas être vide")
	private String confirmPassword;
	
	public UserRegistrationForm() {
		super();
	}

	public UserRegistrationForm(Long id, @NotBlank(message = "Veuilez saisir votre nom") @NotNull String lastname,
			@NotBlank(message = "Veuilez saisir votre prénom") @NotNull String firstname,
			@NotBlank(message = "Veuilez remplir l'adresse email") @NotNull @Email String email,
			@NotBlank(message = "Veuilez remplir l'adresse") @NotNull String address,
			@NotBlank @Size(min = 10, max = 12, message = "Minimum 10 caracteres") String telephone,
			Collection<AppRole> roles,
			@NotBlank(message = "Veuilez saisir votre mot de passe") @NotNull String password,
			@NotBlank(message = "Veuilez resaisir votre mot de passe") @NotNull String confirmPassword) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.address = address;
		this.telephone = telephone;
		this.roles = roles;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public UserRegistrationForm(@NotBlank(message = "Veuilez saisir votre nom") @NotNull String lastname,
			@NotBlank(message = "Veuilez saisir votre prénom") @NotNull String firstname,
			@NotBlank(message = "Veuilez remplir l'adresse email") @NotNull @Email String email,
			@NotBlank(message = "Veuilez remplir l'adresse") @NotNull String address,
			@NotBlank @Size(min = 10, max = 12, message = "Minimum 10 caracteres") String telephone,
			Collection<AppRole> roles,
			@NotBlank(message = "Veuilez saisir votre mot de passe") @NotNull String password,
			@NotBlank(message = "Veuilez resaisir votre mot de passe") @NotNull String confirmPassword) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.address = address;
		this.telephone = telephone;
		this.roles = roles;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Collection<AppRole> getRoles() {
		return roles;
	}

	public void setRoles(Collection<AppRole> roles) {
		this.roles = roles;
	}
}
