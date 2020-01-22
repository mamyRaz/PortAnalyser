package org.analyser.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "system_service_benchmark")
public class SystemServiceBenchmark {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "service_name")
	private String serviceName;
	private int port;
	@Column(name = "current_version")
	private String currentVersion;
	private boolean officiel;
	private boolean tcp;
	private boolean udp;
	private boolean portOpenedNormal = false;
	private boolean portFilteredNormal = false;
	private boolean portClosedNormal = false;
	private String reasonOfAbnormal;
	
	public SystemServiceBenchmark() {
		super();
	}
	

	public SystemServiceBenchmark(Long id, String serviceName, int port, String currentVersion, boolean officiel,
			boolean tcp, boolean udp, boolean portOpenedNormal, boolean portFilteredNormal, boolean portClosedNormal,
			String reasonOfAbnormal) {
		super();
		this.id = id;
		this.serviceName = serviceName;
		this.port = port;
		this.currentVersion = currentVersion;
		this.officiel = officiel;
		this.tcp = tcp;
		this.udp = udp;
		this.portOpenedNormal = portOpenedNormal;
		this.portFilteredNormal = portFilteredNormal;
		this.portClosedNormal = portClosedNormal;
		this.reasonOfAbnormal = reasonOfAbnormal;
	}

	public SystemServiceBenchmark(String serviceName, int port, String currentVersion, boolean officiel, boolean tcp,
			boolean udp, boolean portOpenedNormal, boolean portFilteredNormal, boolean portClosedNormal,
			String reasonOfAbnormal) {
		super();
		this.serviceName = serviceName;
		this.port = port;
		this.currentVersion = currentVersion;
		this.officiel = officiel;
		this.tcp = tcp;
		this.udp = udp;
		this.portOpenedNormal = portOpenedNormal;
		this.portFilteredNormal = portFilteredNormal;
		this.portClosedNormal = portClosedNormal;
		this.reasonOfAbnormal = reasonOfAbnormal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public boolean isPortOpenedNormal() {
		return portOpenedNormal;
	}

	public void setPortOpenedNormal(boolean portOpenedNormal) {
		this.portOpenedNormal = portOpenedNormal;
	}

	public boolean isPortFilteredNormal() {
		return portFilteredNormal;
	}

	public void setPortFilteredNormal(boolean portFilteredNormal) {
		this.portFilteredNormal = portFilteredNormal;
	}

	public boolean isPortClosedNormal() {
		return portClosedNormal;
	}

	public void setPortClosedNormal(boolean portClosedNormal) {
		this.portClosedNormal = portClosedNormal;
	}

	public String getReasonOfAbnormal() {
		return reasonOfAbnormal;
	}

	public void setReasonOfAbnormal(String reasonOfAbnormal) {
		this.reasonOfAbnormal = reasonOfAbnormal;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public boolean isOfficiel() {
		return officiel;
	}


	public void setOfficiel(boolean officiel) {
		this.officiel = officiel;
	}


	public boolean isTcp() {
		return tcp;
	}


	public void setTcp(boolean tcp) {
		this.tcp = tcp;
	}


	public boolean isUdp() {
		return udp;
	}


	public void setUdp(boolean udp) {
		this.udp = udp;
	}
}

