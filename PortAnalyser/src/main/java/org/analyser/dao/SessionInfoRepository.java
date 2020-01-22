package org.analyser.dao;

import java.util.Date;
import java.util.List;

import org.analyser.entities.AppUser;
import org.analyser.entities.SessionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionInfoRepository extends JpaRepository<SessionInfo, Long>{
	
	@Query("select max(si.createdDate) from SessionInfo as si where si.user.id = :user_id")
	public Date findLastSession(@Param("user_id") Long id);
	public List<SessionInfo> findByUserOrderByCreatedDateDesc(AppUser user);
}
