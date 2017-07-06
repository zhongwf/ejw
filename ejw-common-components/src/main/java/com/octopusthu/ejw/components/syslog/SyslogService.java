package com.octopusthu.ejw.components.syslog;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.logging.LogLevel;

import com.octopusthu.ejw.components.syslog.SyslogEntry.Operation;
import com.octopusthu.ejw.components.syslog.SyslogEntry.Principal;
import com.octopusthu.ejw.components.syslog.SyslogEntry.Operation.Result;
import com.octopusthu.ejw.security.util.SecurityContextUtils;
import com.octopusthu.ejw.util.RequestContextUtils;

public class SyslogService {
	protected final Log log = LogFactory.getLog(getClass());

	private Syslog syslog;
	private Map<Object, SyslogEntryTemplate> templates = new Hashtable<Object, SyslogEntryTemplate>();

	public SyslogService(Syslog syslog) {
		this.syslog = syslog;
	}

	public void template(Object key, SyslogEntryTemplate template) {
		templates.put(key, template);
	}

	public SyslogEntryTemplate template(Object key) {
		return templates.get(key);
	}

	public void log(SyslogEntry entry) {
		syslog.log(entry);
	}

	public void log(SyslogEntryTemplate template, LogLevel level, String opTarget, Result opResult, String opContent,
			Principal principal) {
		SyslogEntry entry = null;
		try {
			entry = template.clone();
			entry.setLevel(level);
			entry.setTime(new Date());
			entry.setOperation(new Operation(template.getOperation() != null ? template.getOperation().getName() : null,
					opTarget, opResult, opContent));
			entry.setPrincipal(principal != null ? (Principal) principal.clone() : null);
		} catch (Exception e) {
			log.error("Error logging defaultSyslogEntry!", e);
			return;
		}
		log(entry);
	}

	public void log(SyslogEntryTemplate template, LogLevel level, String opTarget, Result opResult, String opContent) {
		log(template, level, opTarget, opResult, opContent, new Principal(SecurityContextUtils.getUserId(),
				RequestContextUtils.getUserIp(), RequestContextUtils.getUserAgentString()));
	}
}
