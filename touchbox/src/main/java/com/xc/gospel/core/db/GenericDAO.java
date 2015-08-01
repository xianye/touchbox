package com.xc.gospel.core.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

public interface GenericDAO<T, ID extends Serializable> extends GOpenDAO<T, ID>,GReadDAO<T, ID> {

	/**
	 * 返回Hibernate模板
	 * 
	 * @return
	 */
	public HibernateTemplate getHibernateTemplate();

	/**
	 * 执行批量删除、更新等非查询类HQL
	 * 
	 * @param hql
	 * @return
	 */
	public int executeHQL(final String hql);

	/**
	 * 
	 * 执行批量删除、更新等非查询类HQL
	 * 
	 * @param hql
	 *            语句
	 * @param param
	 *            参数
	 * @return 执行记录数
	 * @author James Tang
	 */
	public int executeHQL(final String hql, final Object param);

	/**
	 * 
	 * 执行批量删除、更新等非查询类SQL
	 * 
	 * @param sql
	 *            语句
	 * @return 执行记录数
	 * @author James Tang
	 */
	public int executeSQL(final String sql);

	/**
	 * 
	 * 执行批量删除、更新等非查询类SQL
	 * 
	 * @param sql
	 *            语句
	 * @param param
	 *            参数
	 * @return 执行记录数
	 * @author James Tang
	 */
	public int executeSQL(final String sql, final Object param);
	
	/**
	 * 更新数据
	 * 
	 * @param clazz 类对象
	 * @param setParams 更新参数
	 * @param whereHql where条件
	 * @param whereParamVals where条件参数值
	 * @return 执行记录数
	 */
	public int executeUpdate(Class clazz, Map<String,Object> setParams,String whereHql,List whereParamVals);

}