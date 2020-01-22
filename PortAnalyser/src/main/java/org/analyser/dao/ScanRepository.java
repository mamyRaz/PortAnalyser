package org.analyser.dao;

import java.util.List;

import org.analyser.entities.AppUser;
import org.analyser.entities.Scan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScanRepository extends JpaRepository<Scan, Long>{
	public Scan findScanById(Long id);
	public List<Scan> findByPlatform(String platform);
	public List<Scan> findByUser(AppUser user);
	public List<Scan> findByOrderByCreatedDateDesc();
}
