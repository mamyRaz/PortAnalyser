package org.analyser.services.interfaces;

import java.util.List;

import org.analyser.entities.IPAddress;
import org.analyser.entities.Scan;
import org.analyser.entities.SystemService;
import org.analyser.entities.SystemServiceBenchmark;

public interface IScannerService {
	public Scan saveScan(Scan scan);
	public IPAddress saveIPAddress(IPAddress ipAddress);
	public List<IPAddress> saveAllIPAddress(List<IPAddress> ipAddresses);
	public SystemService saveSystemService(SystemService systemService);
	public List<SystemService> saveAllSystemService(List<SystemService> systemServices);
	public List<Scan> getAllScans();
	public List<Scan> getScanByUserId(Long id);
	public Scan getScanById(Long id);
	public List<IPAddress> getIPAddressByScanId(Long id);
	public List<SystemService> getSystemServiceByIPAddressId(Long id);
	public List<SystemService> getSystemServiceByPortOpenedByIPAddressId(Long id);
	public List<SystemService> getSystemServiceByPortFilteredByIPAddressId(Long id);
	public List<SystemService> getSystemServiceByPortClosedByIPAddressId(Long id);
	public int getDefaultPortByServiceName(String serviceName);
	public String getCurrentVersionByServiceName(String serviceName);
	public SystemServiceBenchmark saveSystemServiceForBenchmark(SystemServiceBenchmark systemServiceBenchmark);
	public SystemServiceBenchmark findSystemServiceForBenchmarkByPort(int port);
}
