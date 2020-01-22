package org.analyser.entities;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ip_address")
public class IPAddress implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "ip_address")
	private InetAddress ipAddress;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "scan_id")
	private Scan scan;
	@OneToMany(mappedBy = "ipAddress")
	private Collection<SystemService> services = new ArrayList<>();
	
	public IPAddress() {
		super();
	}

	public IPAddress(Long id, InetAddress ipAddress, Scan scan, Collection<SystemService> services) {
		super();
		this.id = id;
		this.ipAddress = ipAddress;
		this.scan = scan;
		this.services = services;
	}
	
	public IPAddress(InetAddress ipAddress, Scan scan, Collection<SystemService> services) {
		super();
		this.ipAddress = ipAddress;
		this.scan = scan;
		this.services = services;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Scan getScan() {
		return scan;
	}

	public void setScan(Scan scan) {
		this.scan = scan;
	}

	public Collection<SystemService> getServices() {
		return services;
	}

	public void setServices(Collection<SystemService> services) {
		this.services = services;
	}
	
	@Override
	public String toString() {
		return ipAddress.getHostAddress();
	}
}
