package com.octopusthu.ejw.components.syslog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.logging.LogLevel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DefaultSyslogEntry implements SyslogEntry, Cloneable {

	private LogLevel level;
	private Date time;
	private Module module;
	private Operation operation;
	private Principal principal;
	private Target target;
	private Map<String, String> extra;

	@Override
	public DefaultSyslogEntry clone() throws CloneNotSupportedException {
		DefaultSyslogEntry ret = new DefaultSyslogEntry();
		ret.setLevel(this.getLevel());
		ret.setTime(this.getTime() == null ? null : (Date) this.getTime().clone());
		ret.setModule(this.getModule() == null ? null : this.getModule().clone());
		ret.setOperation(this.getOperation() == null ? null : this.getOperation().clone());
		ret.setPrincipal(this.getPrincipal() == null ? null : this.getPrincipal().clone());
		ret.setTarget(this.getTarget() == null ? null : this.getTarget().clone());
		ret.setExtra(this.getExtra() == null ? null : new HashMap<String, String>(this.getExtra()));
		return ret;
	}

	@AllArgsConstructor
	@Data
	public static class Module implements Cloneable {
		private String app;
		private String module;

		@Override
		public Module clone() throws CloneNotSupportedException {
			return (Module) super.clone();
		}
	}

	@AllArgsConstructor
	@Data
	public static class Operation implements Cloneable {
		private String name;
		private Result result;
		private String content;

		@Override
		public Operation clone() throws CloneNotSupportedException {
			return (Operation) super.clone();
		}

		public static enum Result {
			SUCCESSFUL, FAILURE, UNKNOWN
		}
	}

	@AllArgsConstructor
	@Data
	public static class Principal implements Cloneable {
		private String id;
		private String name;
		private String ip;
		private String ua;

		@Override
		public Principal clone() throws CloneNotSupportedException {
			return (Principal) super.clone();
		}
	}

	@AllArgsConstructor
	@Data
	public static class Target implements Cloneable {
		private String id;
		private String name;

		@Override
		public Target clone() throws CloneNotSupportedException {
			return (Target) super.clone();
		}
	}
}
