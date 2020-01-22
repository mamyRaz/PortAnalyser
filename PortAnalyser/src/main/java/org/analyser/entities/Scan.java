package org.analyser.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class Scan implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "created_date")
	@CreatedDate
	private Date createdDate = new Date();
	private String platform;
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private AppUser user;
	@OneToMany(mappedBy = "scan")
	private Collection<IPAddress> ips = new ArrayList<>();
	
	public Scan() {
		super();
	}

	public Scan(Long id, Date createdDate, String platform, @NotNull AppUser user, Collection<IPAddress> ips) {
		super();
		this.id = id;
		this.createdDate = createdDate;
		this.platform = platform;
		this.user = user;
		this.ips = ips;
	}

	public Scan(Date createdDate, String platform, @NotNull AppUser user, Collection<IPAddress> ips) {
		super();
		this.createdDate = createdDate;
		this.platform = platform;
		this.user = user;
		this.ips = ips;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() throws ParseException {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Collection<IPAddress> getIps() {
		return ips;
	}

	public void setIps(Collection<IPAddress> ips) {
		this.ips = ips;
	}	
}
