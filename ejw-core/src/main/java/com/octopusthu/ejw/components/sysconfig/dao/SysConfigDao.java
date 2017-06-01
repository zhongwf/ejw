package com.octopusthu.ejw.components.sysconfig.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.octopusthu.ejw.components.sysconfig.domain.SysConfig;
import com.octopusthu.ejw.dao.BaseDao;

@Repository
@Lazy
public interface SysConfigDao extends BaseDao<SysConfig> {

}
