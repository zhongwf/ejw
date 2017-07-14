package com.octopusthu.ejw.components.syslog.jpa.repo;

import java.util.List;

import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.octopusthu.ejw.components.syslog.jpa.domain.JpaSyslogEntryEntity;

public interface JpaSyslogRepository extends JpaRepository<JpaSyslogEntryEntity, Long> {
	List<JpaSyslogEntryEntity> findAllByOrderByTimeDesc(Pageable pageable);

	List<JpaSyslogEntryEntity> findByLevelOrderByTimeDesc(@Param("level") LogLevel userType, Pageable pageable);

	List<JpaSyslogEntryEntity> findByPrinIdOrderByTimeDesc(@Param("prinId") String prinId, Pageable pageable);
}
