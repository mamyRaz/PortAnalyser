package org.analyser.dao;

import java.util.List;

import org.analyser.entities.IPAddress;
import org.analyser.entities.SystemService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemServiceRepository extends JpaRepository<SystemService, Long>{
	public List<SystemService> findByIpAddress(IPAddress iPAddress);
	public List<SystemService> findByPortOpenedTrueAndIpAddress(IPAddress iPAddress);
	public List<SystemService> findByPortFilteredTrueAndIpAddress(IPAddress iPAddress);
	public List<SystemService> findByPortClosedTrueAndIpAddress(IPAddress iPAddress);
}
