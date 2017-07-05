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
import javax.persistence.Table;

import org.springframework.boot.logging.LogLevel;

import com.octopusthu.ejw.components.syslog.DefaultSyslogEntry.Operation.Result;

import lombok.Data;

@Entity
@Table(name = "EJW_SYSLOG")
@Data
public class JpaSyslogEntryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEQ", nullable = false)
	private Long seq;

	@Column(name = "LEVEL", nullable = false)
	@Enumerated(EnumType.STRING)
	private LogLevel level;

	@Column(name = "TIME", nullable = false)
	private Date time;

	@Column(name = "APP")
	private String app;

	@Column(name = "MODULE")
	private String module;

	@Column(name = "OP_NAME", nullable = false)
	private String opName;

	@Column(name = "OP_RESULT")
	@Enumerated(EnumType.STRING)
	private Result opResult;

	@Lob
	@Column(name = "OP_CONTENT")
	private String opContent;

	@Column(name = "PRIN_ID")
	private String prinId;

	@Column(name = "PRIN_NAME")
	private String prinName;

	@Column(name = "PRIN_IP")
	private String prinIp;

	@Column(name = "PRIN_UA")
	private String PrinUa;

	@Column(name = "TRG_ID")
	private String trgId;

	@Column(name = "TRG_NAME")
	private String trgName;

	@Lob
	@Column(name = "EXTRA")
	private String extra;
}
