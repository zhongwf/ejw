package com.octopusthu.ejw.components.syslog;

import java.util.Hashtable;
import java.util.Map;

public class SyslogEntryTemplates {
	private Map<Object, SyslogEntryTemplate> templates = new Hashtable<Object, SyslogEntryTemplate>();

	public void put(Object key, SyslogEntryTemplate template) {
		templates.put(key, template);
	}

	public SyslogEntryTemplate get(Object key) {
		return templates.get(key);
	}

}
