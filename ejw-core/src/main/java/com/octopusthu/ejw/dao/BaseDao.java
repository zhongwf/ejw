package com.octopusthu.ejw.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

	public T getById(Serializable id);

	public T getByUniqueKey(String columnName, Object value);

	public T getUniqueResult(Map<String, Object> nameValuePairs);

	public List<T> getListByColumn(String columnName, Object value);

	public List<T> getListByColumns(Map<String, Object> nameValuePairs);

	public List<T> getAll();

	public Serializable save(T t);

	public void update(T t);

	public void saveOrUpdate(T t);

	public void delete(T t);

}
