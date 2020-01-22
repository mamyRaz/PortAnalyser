package org.analyser.dao;

import java.net.InetAddress;
import java.util.List;

import org.analyser.entities.IPAddress;
import org.analyser.entities.Scan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPAddressRepository extends JpaRepository<IPAddress, Long>{
	public IPAddress findIPAddressById(Long id);
	public List<IPAddress> findByIpAddress(InetAddress ipAddress);
	public List<IPAddress> findByScan(Scan scan);
}
