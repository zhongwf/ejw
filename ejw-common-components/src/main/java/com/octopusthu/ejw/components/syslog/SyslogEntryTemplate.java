package com.octopusthu.ejw.components.syslog;

import org.springframework.boot.logging.LogLevel;

public class SyslogEntryTemplate extends SyslogEntry implements Cloneable {
	public SyslogEntryTemplate() {
		super();
	}

	public SyslogEntryTemplate(LogLevel level, Module module) {
		super();
		setLevel(level);
		setModule(module);
	}

	public SyslogEntryTemplate(LogLevel level, Module module, Operation operation) {
		super();
		setLevel(level);
		setModule(module);
		setOperation(operation);
	}

	@Override
	public SyslogEntryTemplate clone() {
		return (SyslogEntryTemplate) super.clone();
	}

	public SyslogEntryTemplate level(LogLevel level) {
		this.setLevel(level);
		return this;
	}

	public SyslogEntryTemplate module(Module module) {
		this.setModule(module);
		return this;
	}

	public SyslogEntryTemplate operation(Operation operation) {
		this.setOperation(operation);
		return this;
	}
}
