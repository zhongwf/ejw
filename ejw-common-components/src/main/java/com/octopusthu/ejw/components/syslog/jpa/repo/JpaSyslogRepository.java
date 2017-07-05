package com.octopusthu.ejw.components.syslog.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.octopusthu.ejw.components.syslog.jpa.domain.JpaSyslogEntryEntity;

public interface JpaSyslogRepository extends JpaRepository<JpaSyslogEntryEntity, Long> {
}
