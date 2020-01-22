package org.analyser.dao;

import org.analyser.entities.SystemServiceBenchmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemServiceBenchmarkRepository extends JpaRepository<SystemServiceBenchmark, Long>{
	public SystemServiceBenchmark findSystemServiceBenchmarkByServiceName(String serviceName);
	public SystemServiceBenchmark findSystemServiceBenchmarkByPort(int port);
}
