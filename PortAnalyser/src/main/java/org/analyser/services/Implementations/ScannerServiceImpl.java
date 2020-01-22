package org.analyser.services.Implementations;

import java.util.List;

import org.analyser.dao.AppUserRepository;
import org.analyser.dao.IPAddressRepository;
import org.analyser.dao.ScanRepository;
import org.analyser.dao.SystemServiceBenchmarkRepository;
import org.analyser.dao.SystemServiceRepository;
import org.analyser.entities.AppUser;
import org.analyser.entities.IPAddress;
import org.analyser.entities.Scan;
import org.analyser.entities.SystemService;
import org.analyser.entities.SystemServiceBenchmark;
import org.analyser.services.interfaces.IScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScannerServiceImpl implements IScannerService{
	@Autowired
	private ScanRepository scanRepository;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private SystemServiceBenchmarkRepository systemServiceBenchmarkRepository;
	@Autowired
	private IPAddressRepository iPAddressRepository;
	@Autowired
	private SystemServiceRepository systemServiceRepository;
	
	@Override
	public Scan saveScan(Scan scan) {
		return scanRepository.save(scan);
	}

	@Override
	public List<Scan> getAllScans() {
		return scanRepository.findByOrderByCreatedDateDesc();
	}

	@Override
	public List<Scan> getScanByUserId(Long id) {
		AppUser appUser = appUserRepository.findAppUserById(id);
		return scanRepository.findByUser(appUser);
	}

	@Override
	public List<IPAddress> getIPAddressByScanId(Long id) {
		Scan scan = scanRepository.findScanById(id);
		return iPAddressRepository.findByScan(scan);
	}

	/**
	 * On recupere tous les services, appartenant a une adresse IP et ces services qui tournent sur un port 
	 */
	@Override
	public List<SystemService> getSystemServiceByIPAddressId(Long id) {
		IPAddress iPAddress = iPAddressRepository.findIPAddressById(id);
		return systemServiceRepository.findByIpAddress(iPAddress);
	}

	/**
	 * On recupere tous les services dont les ports sont ouverts selon l'Id de l'adresse IP
	 */
	@Override
	public List<SystemService> getSystemServiceByPortOpenedByIPAddressId(Long id) {
		IPAddress iPAddress = iPAddressRepository.findIPAddressById(id);
		return systemServiceRepository.findByPortOpenedTrueAndIpAddress(iPAddress);
	}

	/**
	 * On recupere tous les services dont les ports sont filtrEs selon l'Id de l'adresse IP
	 */
	@Override
	public List<SystemService> getSystemServiceByPortFilteredByIPAddressId(Long id) {
		IPAddress iPAddress = iPAddressRepository.findIPAddressById(id);
		return systemServiceRepository.findByPortFilteredTrueAndIpAddress(iPAddress);
	}

	/**
	 * On recupere tous les services dont les ports sont fermEs selon l'Id de l'adresse IP
	 */
	@Override
	public List<SystemService> getSystemServiceByPortClosedByIPAddressId(Long id) {
		IPAddress iPAddress = iPAddressRepository.findIPAddressById(id);
		return systemServiceRepository.findByPortClosedTrueAndIpAddress(iPAddress);
	}

	@Override
	public int getDefaultPortByServiceName(String serviceName) {
		SystemServiceBenchmark systemServiceBenchmark = systemServiceBenchmarkRepository.findSystemServiceBenchmarkByServiceName(serviceName);
		return systemServiceBenchmark.getPort();
	}

	@Override
	public SystemServiceBenchmark saveSystemServiceForBenchmark(SystemServiceBenchmark systemServiceBenchmark) {
		return systemServiceBenchmarkRepository.save(systemServiceBenchmark);
	}

	@Override
	public String getCurrentVersionByServiceName(String serviceName) {
		SystemServiceBenchmark systemServiceBenchmark = systemServiceBenchmarkRepository.findSystemServiceBenchmarkByServiceName(serviceName);
		return systemServiceBenchmark.getCurrentVersion();
	}

	@Override
	public IPAddress saveIPAddress(IPAddress ipAddress) {
		return iPAddressRepository.save(ipAddress);
	}

	@Override
	public SystemService saveSystemService(SystemService systemService) {
		return systemServiceRepository.save(systemService);
	}

	@Override
	public List<IPAddress> saveAllIPAddress(List<IPAddress> ipAddresses) {
		return iPAddressRepository.saveAll(ipAddresses);
	}

	@Override
	public List<SystemService> saveAllSystemService(List<SystemService> systemServices) {
		return systemServiceRepository.saveAll(systemServices);
	}

	@Override
	public Scan getScanById(Long id) {
		return scanRepository.findScanById(id);
	}

	@Override
	public SystemServiceBenchmark findSystemServiceForBenchmarkByPort(int port) {
		return systemServiceBenchmarkRepository.findSystemServiceBenchmarkByPort(port);
	}
	
}
