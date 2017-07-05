package com.octopusthu.ejw.components.syslog;

public interface Syslog<E extends SyslogEntry> {
	public void log(E entry);
}
