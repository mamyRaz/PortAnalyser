package org.analyser.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table
public class SessionInfo implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private AppUser user;
	@Column(name = "created_date")
	@CreatedDate
	private Date createdDate = new Date();
	
	public SessionInfo() {
		super();
	}

	public SessionInfo(Long id, AppUser user, Date createdDate) {
		super();
		this.id = id;
		this.user = user;
		this.createdDate = createdDate;
	}

	public SessionInfo(AppUser user, Date createdDate) {
		super();
		this.user = user;
		this.createdDate = createdDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
