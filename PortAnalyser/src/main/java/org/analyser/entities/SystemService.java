package org.analyser.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "system_service")
public class SystemService implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ip_address_id")
	private IPAddress ipAddress;
	private int port;
	private String version;
	private boolean portOpened = false;
	private boolean portFiltered = false;
	private boolean portClosed = false;
	
	public SystemService() {
		super();
	}
	
	public SystemService(Long id, IPAddress ipAddress, int port, String version, boolean portOpened,
			boolean portFiltered, boolean portClosed) {
		super();
		this.id = id;
		this.ipAddress = ipAddress;
		this.port = port;
		this.version = version;
		this.portOpened = portOpened;
		this.portFiltered = portFiltered;
		this.portClosed = portClosed;
	}

	public SystemService(IPAddress ipAddress, int port, String version, boolean portOpened, boolean portFiltered,
			boolean portClosed) {
		super();
		this.ipAddress = ipAddress;
		this.port = port;
		this.version = version;
		this.portOpened = portOpened;
		this.portFiltered = portFiltered;
		this.portClosed = portClosed;
	}

	public SystemService(IPAddress ipAddress, int port) {
		super();
		this.ipAddress = ipAddress;
		this.port = port;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IPAddress getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(IPAddress ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isPortOpened() {
		return portOpened;
	}

	public void setPortOpened(boolean portOpened) {
		this.portOpened = portOpened;
	}

	public boolean isPortFiltered() {
		return portFiltered;
	}

	public void setPortFiltered(boolean portFiltered) {
		this.portFiltered = portFiltered;
	}

	public boolean isPortClosed() {
		return portClosed;
	}

	public void setPortClosed(boolean portClosed) {
		this.portClosed = portClosed;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
