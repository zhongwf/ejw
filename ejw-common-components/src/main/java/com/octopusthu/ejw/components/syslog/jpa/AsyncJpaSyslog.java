package com.octopusthu.ejw.components.syslog.jpa;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;

import com.octopusthu.ejw.components.syslog.SyslogEntry;
import com.octopusthu.ejw.components.syslog.SyslogEntry.Module;
import com.octopusthu.ejw.components.syslog.SyslogEntry.Operation;
import com.octopusthu.ejw.components.syslog.SyslogEntry.Principal;
import com.octopusthu.ejw.components.syslog.Syslog;
import com.octopusthu.ejw.components.syslog.jpa.domain.JpaSyslogEntryEntity;
import com.octopusthu.ejw.components.syslog.jpa.repo.JpaSyslogRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AsyncJpaSyslog implements Syslog {
	protected final Log log = LogFactory.getLog(getClass());

	private JpaSyslogRepository repo;

	@Override
	@Async
	public void log(SyslogEntry entry) {
		try {
			repo.save(entryToEntity(entry));
		} catch (Exception e) {
			log.error("Error logging defaultSyslogEntry!", e);
		}
	}

	protected JpaSyslogEntryEntity entryToEntity(SyslogEntry entry) {
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
			entity.setOpTarget(operation.getTarget());
			entity.setOpResult(operation.getResult());
			entity.setOpContent(operation.getContent());
		}
		Principal principal = entry.getPrincipal();
		if (principal != null) {
			entity.setPrinId(principal.getId());
			entity.setPrinIp(principal.getIp());
			entity.setPrinUa(principal.getUa());
		}
		Map<String, String> extra = entry.getExtra();
		if (MapUtils.isNotEmpty(extra)) {
			entity.setExtra(extra.toString());
		}
		return entity;
	}

}
