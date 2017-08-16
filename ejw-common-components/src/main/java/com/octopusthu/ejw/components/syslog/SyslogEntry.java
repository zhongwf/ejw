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
public class SyslogEntry implements Cloneable {

	private LogLevel level;
	private Date time;
	private Module module;
	private Operation operation;
	private Principal principal;
	private Map<String, String> extra;

	@Override
	public Object clone() {
		SyslogEntry ret = null;
		try {
			ret = (SyslogEntry) super.clone();
			ret.setLevel(this.getLevel());
			ret.setTime(this.getTime() == null ? null : (Date) this.getTime().clone());
			ret.setModule(this.getModule() == null ? null : (Module) this.getModule().clone());
			ret.setOperation(this.getOperation() == null ? null : (Operation) this.getOperation().clone());
			ret.setPrincipal(this.getPrincipal() == null ? null : (Principal) this.getPrincipal().clone());
			ret.setExtra(this.getExtra() == null ? null : new HashMap<String, String>(this.getExtra()));
		} catch (CloneNotSupportedException e) {
		}
		return ret;
	}

	@AllArgsConstructor
	@Data
	public static class Module implements Cloneable {
		private String app;
		private String module;

		@Override
		public Object clone() {
			Module ret = null;
			try {
				ret = (Module) super.clone();
			} catch (CloneNotSupportedException e) {
			}
			return ret;
		}
	}

	@AllArgsConstructor
	@Data
	public static class Operation implements Cloneable {
		private String name;
		private String target;
		private Result result;
		private String content;

		@Override
		public Object clone() {
			Operation ret = null;
			try {
				ret = (Operation) super.clone();
			} catch (CloneNotSupportedException e) {
			}
			return ret;
		}

		public static enum Result {
			SUCCESS, FAILURE, UNKNOWN
		}
	}

	@AllArgsConstructor
	@Data
	public static class Principal implements Cloneable {
		private String id;
		private String ip;
		private String ua;

		@Override
		public Object clone() {
			Principal ret = null;
			try {
				ret = (Principal) super.clone();
			} catch (CloneNotSupportedException e) {
			}
			return ret;
		}

		public static final Principal SYSTEM = new Principal("SYSTEM", null, null);
	}
}
