package com.xc.gospel.core.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface GOpenDAO<T, ID extends Serializable> extends
		GOpenReadDAO<T, ID> {

	/**
	 * 插入一个对象.
	 * 
	 * @param entity
	 *            the object to save
	 */
	public void save(final T entity);

	/**
	 * 
	 * 保存一个集合
	 * 
	 * @param entities
	 */
	public void saveAll(Collection entities);
	
	/**
	 * 更新一个对象.
	 * 
	 * @param entity
	 *            the object to update
	 */
	public void update(final T entity);

	/**
	 * 删除一个对象.
	 * 
	 * @param entity
	 *            the object to delete
	 */
	public void delete(final T entity);

	/**
	 * 删除一个对象.
	 * 
	 * @param entity
	 *            the object to delete
	 * @param paramHqls
	 * @param paramValues
	 */
	public int delete(Class<T> entity,String[] paramHqls,List paramValues);

	/**
	 * 根据对应的id数组删除数据（基本数据类型）
	 * 
	 * @param entity
	 * @param idName
	 * @param ids
	 * @return
	 */
	public int deleteByIds(Class<T> entity, String idName, Object[] ids);

}