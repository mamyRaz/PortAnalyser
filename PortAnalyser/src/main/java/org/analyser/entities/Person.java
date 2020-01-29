package org.analyser.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"email"})})
public class Person implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	@NotNull
	private String lastname;
	@NotNull
	private String firstname;
	@Email
	@NotNull
	private String email;
	@NotNull
	private String address;
	@Size(min= 10, max = 12)
	private String telephone;
	
	public Person() {
		super();
	}

	public Person(Long id, @NotNull String lastname, @NotNull String firstname, @NotNull String email,
			@NotNull String address, @Size(min = 10, max = 12, message = "Au maximum 10 chiffres") String telephone) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.address = address;
		this.telephone = telephone;
	}

	public Person(@NotNull String lastname, @NotNull String firstname, @NotNull String email, @NotNull String address,
			@Size(min = 10, max = 12, message = "Au maximum 10 chiffres") String telephone) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.address = address;
		this.telephone = telephone;
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
}
