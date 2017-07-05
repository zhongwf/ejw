package com.octopusthu.ejw.components.syslog.jpa.config;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import com.octopusthu.ejw.components.syslog.jpa.AsyncJpaSyslog;
import com.octopusthu.ejw.components.syslog.jpa.repo.JpaSyslogRepository;

@ConditionalOnProperty(value = "ejw.components.syslog.impl", havingValue = "jpa")
@Configuration
@EnableJpaRepositories("${ejw.components.syslog.jpa.basePackages}")
@EnableAsync
public class JpaSyslogConfig {
	@Inject
	JpaSyslogRepository repo;

	@Bean
	public AsyncJpaSyslog asyncJpaSyslog() {
		return new AsyncJpaSyslog(repo);
	}
}
