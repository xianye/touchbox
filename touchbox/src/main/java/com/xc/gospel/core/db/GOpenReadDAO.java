package com.xc.gospel.core.db;

import java.io.Serializable;
import java.util.List;

public interface GOpenReadDAO<T, ID extends Serializable> {

	/**
	 * 获取一个T类型对象
	 * 
	 * @param entity
	 * @param id
	 * @return
	 */
	public T get(final Class<T> entity, final ID id);

	public List<T> findAll(final Class<T> entity);

}