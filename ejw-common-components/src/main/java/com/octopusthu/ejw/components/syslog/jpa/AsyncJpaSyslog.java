package com.octopusthu.ejw.components.syslog.jpa;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;

import com.octopusthu.ejw.components.syslog.DefaultSyslogEntry;
import com.octopusthu.ejw.components.syslog.DefaultSyslogEntry.Module;
import com.octopusthu.ejw.components.syslog.DefaultSyslogEntry.Operation;
import com.octopusthu.ejw.components.syslog.DefaultSyslogEntry.Operation.Result;
import com.octopusthu.ejw.components.syslog.DefaultSyslogEntry.Principal;
import com.octopusthu.ejw.components.syslog.DefaultSyslogEntry.Target;
import com.octopusthu.ejw.components.syslog.Syslog;
import com.octopusthu.ejw.components.syslog.jpa.domain.JpaSyslogEntryEntity;
import com.octopusthu.ejw.components.syslog.jpa.repo.JpaSyslogRepository;
import com.octopusthu.ejw.security.util.SecurityContextUtils;
import com.octopusthu.ejw.util.RequestContextUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AsyncJpaSyslog implements Syslog<DefaultSyslogEntry> {
	protected final Log log = LogFactory.getLog(getClass());

	private JpaSyslogRepository repo;

	@Override
	@Async
	public void log(DefaultSyslogEntry entry) {
		try {
			repo.save(entryToEntity(entry));
		} catch (Exception e) {
			log.error("Error logging defaultSyslogEntry!", e);
		}
	}

	public void log(DefaultSyslogEntry template, String opName, Result opResult, String opContent, String prinId,
			String prinName, String trgId, String trgName) {
		try {
			DefaultSyslogEntry entry = template.clone();
			entry.setOperation(new Operation(opName, opResult, opContent));
			entry.setPrincipal(new Principal(prinId, prinName, RequestContextUtils.getUserIp(),
					RequestContextUtils.getUserAgentString()));
			entry.setTarget(new Target(trgId, trgName));
			entry.setTime(new Date());
			log(entry);
		} catch (Exception e) {
			log.error("Error logging defaultSyslogEntry!", e);
		}
	}

	public void log(DefaultSyslogEntry template, String opName, Result opResult, String opContent, String trgId,
			String trgName) {
		log(template, opName, opResult, opContent, SecurityContextUtils.getUserId(), null, trgId, trgName);
	}

	private JpaSyslogEntryEntity entryToEntity(DefaultSyslogEntry entry) {
		JpaSyslogEntryEntity entity = new JpaSyslogEntryEntity();
		entity.setLevel(entry.getLevel());
		entity.setTime(entry.getTime());
		Module module = entry.getModule();
		if (module != null) {
			entity.setApp(module.getApp());
			entity.setModule(module.getModule());
		}
		Operation operation = entry.getOperation();
		if (operation != null) {
			entity.setOpName(operation.getName());
			entity.setOpResult(operation.getResult());
			entity.setOpContent(operation.getContent());
		}
		Principal principal = entry.getPrincipal();
		if (principal != null) {
			entity.setPrinId(principal.getId());
			entity.setPrinName(principal.getName());
			entity.setPrinIp(principal.getIp());
			entity.setPrinUa(principal.getUa());
		}
		Target target = entry.getTarget();
		if (target != null) {
			entity.setTrgId(target.getId());
			entity.setTrgName(target.getName());
		}
		Map<String, String> extra = entry.getExtra();
		if (MapUtils.isNotEmpty(extra)) {
			entity.setExtra(extra.toString());
		}
		return entity;
	}

}
