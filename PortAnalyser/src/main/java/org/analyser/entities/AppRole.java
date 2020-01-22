package org.analyser.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role")
public class AppRole implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Column(name = "rolename")
	private String roleName;
	
	public AppRole() {
		super();
	}

	public AppRole(Long id, @NotNull String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}

	public AppRole(@NotNull String roleName) {
		super();
		this.roleName = roleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
