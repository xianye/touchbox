package com.xc.gospel.core.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.NullableType;

import com.xc.gospel.core.vo.PaginationSupport;

public interface GReadDAO<T, ID extends Serializable> extends
		GOpenReadDAO<T, ID> {
	/**
	 * 执行hql语句获取单个对象
	 * 
	 * @param hql
	 * @return
	 */
	public T getHQLUnique(final String hql);

	/**
	 * 执行hql语句获取单个对象
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public T getHQLUnique(final String hql, final Object param);

	/**
	 * 执行sql语句获取单个对象
	 * 
	 * @param sql
	 * @return
	 */
	public T getSQLUnique(final String sql);

	/**
	 * 执行sql语句获取单个对象
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	public T getSQLUnique(final String sql, final Object param);

	/**
	 * 
	 * 获取hql查询记录总数
	 * 
	 * @param hql
	 *            语句
	 * @param param
	 *            参数
	 * @return 查询记录总数
	 * @author James Tang
	 */
	public long getHQLCount(final String hql, final Object param);

	/**
	 * 
	 * 获取sql查询记录总数
	 * 
	 * @param sql
	 *            语句
	 * @param param
	 *            参数
	 * @return 查询记录总数
	 * @author James Tang
	 */
	public long getSQLCount(final String sql, final Object param);

	/**
	 * 获取hql查询分页结果对象
	 * 
	 * @param hql
	 *            语句
	 * @param param
	 *            参数
	 * @param firstResults
	 *            记录起始索引
	 * @param maxResults
	 *            单页记录数
	 * @return hql查询分页结果对象
	 */
	public PaginationSupport getHQLPagination(final String hql,
			final Object param, final int firstResults, final int maxResults);

	/**
	 * 获取hql查询分页结果对象
	 * 
	 * @param hql
	 *            语句
	 * @param countHql
	 *            总数语句
	 * @param param
	 *            参数
	 * @param firstResults
	 *            记录起始索引
	 * @param maxResults
	 *            单页记录数
	 * @return hql查询分页结果对象
	 */
	public PaginationSupport getHQLPagination(final String hql,
			final String countHql, final Object param, final int firstResults,
			final int maxResults);

	/**
	 * 获取sql查询分页结果对象
	 * 
	 * @param sql
	 *            语句
	 * @param countSql
	 *            统计总数语句
	 * @param param
	 *            参数
	 * @param entityMap
	 *            实体别名与Class映射[key=alias,value=POJOCLASS]
	 * @param firstResults
	 *            记录起始索引
	 * @param maxResults
	 *            单页记录数
	 * @return sql查询分页结果对象
	 */
	public PaginationSupport getSQLPagination(final String sql,
			final String countSql, final Object param,
			final Map<String, Class> entityMap, final int firstResults,
			final int maxResults);

	/**
	 * 获取sql查询分页结果对象
	 * 
	 * @param sql
	 *            语句
	 * @param countSql
	 *            统计总数语句
	 * @param param
	 *            参数
	 * @param scalarMap
	 *            结果映射类型
	 * @param resultTransformer
	 *            结果
	 * @param firstResults
	 *            记录起始索引
	 * @param maxResults
	 *            单页记录数
	 * @return sql查询分页结果对象
	 */
	public PaginationSupport getSQLPagination(final String sql,
			final String countSql, final Object param,
			final Map<String, NullableType> scalarMap,
			final ResultTransformer resultTransformer, final int firstResults,
			final int maxResults);

	/**
	 * 获取sql查询分页结果对象(多表union)
	 * 
	 * @param sql
	 *            查询sql语句
	 * @param countSql
	 *            统计总数sql语句
	 * @param orderbySql
	 *            查询排序sql语句
	 * @param param
	 *            参数
	 * @param scalarMap
	 *            结果映射类型
	 * @param resultTransformer
	 *            结果
	 * @param page
	 *            页码
	 * @param maxResults
	 *            单页记录数
	 * @param cacheable
	 *            是否缓存结果
	 * @return sql查询分页结果对象
	 */
	public PaginationSupport getSQLPaginationOnTables(
			final List<String> queryTableNames, final String sql,
			final String countSql, final String orderbySql, final Object param,
			final Map<String, NullableType> scalarMap,
			final ResultTransformer resultTransformer, final int page,
			final int maxResults, boolean cacheable);

	/**
	 * 获取hql查询记录列表
	 * 
	 * @param hql
	 *            语句
	 * @param param
	 *            参数
	 * @param maxResults
	 *            单页记录数
	 * @return 查询记录列表
	 */
	public List findHQL(final String hql, final Object param,
			final int maxResults);

	/**
	 * 获取hql查询记录列表
	 * 
	 * @param hql
	 *            语句
	 * @param param
	 *            参数
	 * @param firstResults
	 *            记录起始索引
	 * @param maxResults
	 *            单页记录数
	 * @return 查询记录列表
	 */
	public List findHQL(final String hql, final Object param,
			final int firstResults, final int maxResults);

	/**
	 * 获取sql查询记录列表
	 * 
	 * @param sql
	 *            语句
	 * @param param
	 *            参数
	 * @param entityMap
	 *            实体别名与Class映射[key=alias,value=POJOCLASS]
	 * @param maxResults
	 *            单页记录数
	 * @return 查询记录列表
	 */
	public List findSQL(final String sql, final Object param,
			final Map<String, Class> entityMap, final int maxResults);

	/**
	 * 获取sql查询记录列表
	 * 
	 * @param sql
	 *            语句
	 * @param param
	 *            参数
	 * @param entityMap
	 *            实体别名与Class映射[key=alias,value=POJOCLASS]
	 * @return 查询记录列表
	 */
	public List findSQL(final String sql, final Object param,
			final Map<String, Class> entityMap);

	/**
	 * 获取sql查询记录列表
	 * 
	 * @param sql
	 *            语句
	 * @param param
	 *            参数
	 * @param scalarMap
	 *            结果映射类型
	 * @param resultTransformer
	 *            结果
	 * @param firstResults
	 *            记录起始索引
	 * @param maxResults
	 *            单页记录数
	 * @return sql查询记录列表
	 */
	public List findSQL(final String sql, final Object param,
			final Map<String, NullableType> scalarMap,
			final ResultTransformer resultTransformer, final int firstResults,
			final int maxResults);
}