package com.octopusthu.ejw.components.sysconfig.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.octopusthu.ejw.components.sysconfig.dao.SysConfigDao;
import com.octopusthu.ejw.components.sysconfig.domain.SysConfig;

@Service
@Lazy
@Transactional("sysConfigTxManager")
public class SysConfigService {

	@Autowired
	SysConfigDao sysConfigDao;

	public String getConfigItemValue(String sectionName, String itemName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("section_name", sectionName);
		map.put("item_name", itemName);
		SysConfig sysConfig = sysConfigDao.getUniqueResult(map);
		if (sysConfig == null) {
			return null;
		}
		return sysConfig.getItem_value();
	}
}
