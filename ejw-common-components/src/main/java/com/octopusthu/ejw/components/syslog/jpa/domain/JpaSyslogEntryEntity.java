package com.octopusthu.ejw.components.syslog.jpa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.boot.logging.LogLevel;

import com.octopusthu.ejw.components.syslog.SyslogEntry.Operation.Result;

import lombok.Data;

@Entity
@Table(name = "EJW_SYSLOG")
@Data
public class JpaSyslogEntryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSysLog_generator")
	@SequenceGenerator(name = "jpaSysLog_generator", sequenceName = "SEQ_EJW_SYSLOG", allocationSize = 1)
	@Column(name = "SEQ", nullable = false)
	private Long seq;

	@Column(name = "LOG_LEVEL", nullable = false)
	@Enumerated(EnumType.STRING)
	private LogLevel level;

	@Column(name = "LOG_TIME", nullable = false)
	private Date time;

	@Column(name = "APP")
	private String app;

	@Column(name = "MODULE")
	private String module;

	@Column(name = "OP_NAME", nullable = false)
	private String opName;

	@Column(name = "OP_TARGET")
	private String opTarget;

	@Column(name = "OP_RESULT")
	@Enumerated(EnumType.STRING)
	private Result opResult;

	@Lob
	@Column(name = "OP_CONTENT")
	private String opContent;

	@Column(name = "PRIN_ID")
	private String prinId;

	@Column(name = "PRIN_IP")
	private String prinIp;

	@Column(name = "PRIN_UA")
	private String prinUa;

	@Lob
	@Column(name = "EXTRA")
	private String extra;
}
