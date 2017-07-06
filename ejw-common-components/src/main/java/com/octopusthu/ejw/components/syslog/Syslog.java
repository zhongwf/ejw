package com.octopusthu.ejw.components.syslog;

import org.springframework.boot.logging.LogLevel;

import com.octopusthu.ejw.components.syslog.SyslogEntry.Operation.Result;
import com.octopusthu.ejw.components.syslog.SyslogEntry.Principal;

public interface Syslog {
	public void log(SyslogEntry entry);

	public default void log(SyslogEntryTemplate template, LogLevel level, String opTarget, Result opResult,
			String opContent, Principal principal) {
	}

	public default void log(SyslogEntryTemplate template, LogLevel level, String opTarget, Result opResult,
			String opContent) {
	}
}
