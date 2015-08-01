package com.xc.gospel.core.db.hibernate;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.NullableType;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xc.gospel.core.db.GenericDAO;
import com.xc.gospel.core.util.PaginationUtils;
import com.xc.gospel.core.vo.PaginationSupport;

public class GenericHibernateDAO<T, ID extends Serializable> extends
		HibernateDaoSupport implements GenericDAO<T, ID> {

	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	public void saveAll(Collection entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	public int delete(Class<T> entity, String[] paramHqls, List paramValues) {
		StringBuilder hql = new StringBuilder("delete from ");
		hql.append(entity.getName()).append(" where 1=1 ");
		if (!ArrayUtils.isEmpty(paramHqls)) {
			for (int i = 0; i < paramHqls.length; i++) {
				hql.append(paramHqls[i]);
			}
		}
		return this.executeHQL(hql.toString(),
				paramValues.toArray(new Object[] {}));
	}

	public int deleteByIds(Class<T> entity, String idName, Object[] ids) {
		StringBuilder hql = new StringBuilder("delete from ");
		hql.append(entity.getName());
		if (!ArrayUtils.isEmpty(ids)) {
			hql.append(" where ").append(idName).append(" in (");
			String startFlag = "", endFlag = "";
			if (ids[0] instanceof String) {
				startFlag = endFlag = "'";
			}
			for (Object id : ids) {
				hql.append(startFlag).append(id).append(endFlag).append(",");
			}
			hql.deleteCharAt(hql.length() - 1);
			hql.append(")");
		}
		return this.executeHQL(hql.toString());
	}

	public T get(Class<T> entity, ID id) {
		return (T) getHibernateTemplate().get(entity, id);
	}

	public List<T> findAll(Class<T> entity) {
		return getHibernateTemplate().find("from " + entity.getName());
	}

	/**
	 * 向Query对象 set parameter
	 * 
	 * @param query
	 * @param param
	 */
	private void addParameters(Query query, Object param) {
		if (param != null) {
			if (param instanceof Object[]) {
				Object[] params = (Object[]) param;
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
			} else if (param instanceof List) {
				List params = (List) param;
				for (int i = 0; i < params.size(); i++) {
					query.setParameter(i, params.get(i));
				}
			} else {
				query.setParameter(0, param);
			}
		}
	}

	public T getHQLUnique(String hql) {
		return getHQLUnique(hql, null);
	}

	public T getHQLUnique(String hql, Object param) {
		List list = null;
		if (param != null) {
			if (param instanceof Object[]) {
				list = getHibernateTemplate().find(hql, (Object[]) param);
			} else {
				list = getHibernateTemplate().find(hql, param);
			}
		} else {
			list = getHibernateTemplate().find(hql);
		}
		if (list != null && !list.isEmpty()) {
			return (T) list.get(0);
		}
		return null;
	}

	public T getSQLUnique(final String sql) {
		return getSQLUnique(sql, null);
	}

	public T getSQLUnique(final String sql, final Object param) {
		return (T) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				addParameters(query, param);
				return query.uniqueResult();
			}
		});
	}

	public int executeHQL(final String hql) {
		return executeHQL(hql, null);
	}

	public int executeHQL(final String hql, final Object param) {
		return ((Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query query = s.createQuery(hql);
						addParameters(query, param);
						return new Integer(query.executeUpdate());
					}
				})).intValue();
	}

	public int executeSQL(final String sql) {
		return executeSQL(sql, null);
	}

	public int executeSQL(final String sql, final Object param) {
		return ((Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query query = s.createSQLQuery(sql);
						addParameters(query, param);
						return new Integer(query.executeUpdate());
					}
				})).intValue();
	}

	public long getHQLCount(final String hql, final Object param) {
		List list = null;
		if (param != null) {
			if (param instanceof Object[]) {
				list = getHibernateTemplate().find(hql, (Object[]) param);
			} else {
				list = getHibernateTemplate().find(hql, param);
			}
		} else {
			list = getHibernateTemplate().find(hql);
		}
		if (list == null)
			return -1;
		Long total = (Long) list.get(0);
		return total;
	}

	public long getSQLCount(String sql, Object param) {
		BigInteger total = (BigInteger) getSQLUnique(sql, param);
		if (total != null) {
			return total.longValue();
		}
		return 0;
	}

	/**
	 * 将select * from语句转换为select count(*) from
	 * 
	 * @param queryString
	 * @return 增加count的hql或sql语句
	 */
	private String addCount(String queryString) {
		// 匹配(select (a|a.*|a.id|a,b|a.*,b.*|a.id,b.id|*|函数字符))? from
		Pattern selectPattern = Pattern
				.compile("(([sS][eE][lL][eE][cC][tT])\\s+(\\w+(\\.(\\*|\\w+))?(\\,\\w+(\\.(\\*|\\w+))?)*|\\*|.+)\\s+)?([fF][rR][oO][mM])");

		// test Pattern
		// Matcher matcher = selectPattern.matcher(queryString);
		// while (matcher.find()) {
		// System.out.println("group :" + matcher.group());
		// }

		return queryString.replaceAll(selectPattern.pattern(),
				"select count(*) from");
	}

	/**
	 * 删除查询语句中order by子串
	 * 
	 * @param queryString
	 *            hql或sql
	 * @return 过滤后的查询语句
	 */
	private String delOrder(String queryString) {
		// 匹配order by (id|a.id)+ (desc|asc)?
		// Pattern orderPattern = Pattern
		// .compile("([oO][rR][dD][eE][rR])\\s+([bB][yY])\\s+\\w+(\\.\\w+)?(\\s+([dD][eE]|[aA])[sS][cC])?(\\s*,\\s*\\w+(\\.\\w+)?(\\s+([dD][eE]|[aA])[sS][cC])?)*");
		Pattern orderPattern = Pattern
				.compile("([oO][rR][dD][eE][rR])\\s+([bB][yY])\\s+.+");

		// test Pattern
		// Matcher matcher = orderPattern.matcher(queryString);
		// while (matcher.find()) {
		// System.out.println("group :" + matcher.group());
		// }

		return queryString.replaceAll(orderPattern.pattern(), "");
	}

	public PaginationSupport getHQLPagination(final String hql,
			final Object param, final int firstResults, final int maxResults) {
		String countHql = delOrder(hql);
		countHql = addCount(countHql);
		return getHQLPagination(hql, countHql, param, firstResults, maxResults);
	}

	public PaginationSupport getHQLPagination(final String hql,
			final String countHql, final Object param, final int firstResults,
			final int maxResults) {
		return (PaginationSupport) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query query = s.createQuery(countHql);
						addParameters(query, param);
						Long total = (Long) query.uniqueResult();
						List items = null;
						if (total > 0) {
							query = null;
							query = s.createQuery(hql);
							addParameters(query, param);
							if (maxResults > 0) {
								query.setFirstResult(firstResults);
								query.setMaxResults(maxResults);
							}
							items = query.list();
						}
						PaginationSupport ps = new PaginationSupport(items,
								total.intValue(), maxResults, firstResults);
						return ps;
					}
				});
	}

	public PaginationSupport getSQLPagination(final String sql,
			final String countSql, final Object param,
			final Map<String, Class> entityMap, final int firstResults,
			final int maxResults) {
		return (PaginationSupport) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						SQLQuery query = s.createSQLQuery(countSql);
						addParameters(query, param);
						BigInteger total = (BigInteger) query.uniqueResult();
						List items = null;
						if (total.intValue() > 0) {
							query = null;
							query = s.createSQLQuery(sql);
							addParameters(query, param);
							if (maxResults > 0) {
								query.setFirstResult(firstResults);
								query.setMaxResults(maxResults);
							}
							if (MapUtils.isNotEmpty(entityMap)) {
								for (String key : entityMap.keySet()) {
									query.addEntity(key, entityMap.get(key));
								}
							}
							items = query.list();
						}
						PaginationSupport ps = new PaginationSupport(items,
								total.intValue(), maxResults, firstResults);
						return ps;
					}
				});
	}

	public PaginationSupport getSQLPagination(final String sql,
			final String countSql, final Object param,
			final Map<String, NullableType> scalarMap,
			final ResultTransformer resultTransformer, final int firstResults,
			final int maxResults) {
		return (PaginationSupport) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						SQLQuery query = s.createSQLQuery(countSql);
						addParameters(query, param);
						List totals = query.list();
						int totalVal = 0;
						for (Object total : totals) {
							if (total instanceof BigInteger) {
								totalVal += ((BigInteger) total).intValue();
							} else if (total instanceof Integer) {
								totalVal += ((Integer) total).intValue();
							} else if (total instanceof Long) {
								totalVal += ((Long) total).intValue();
							}
						}
						List items = null;
						if (totalVal > 0) {
							query = null;
							query = s.createSQLQuery(sql);
							addParameters(query, param);
							if (maxResults > 0) {
								query.setFirstResult(firstResults);
								query.setMaxResults(maxResults);
							}
							if (MapUtils.isNotEmpty(scalarMap)) {
								for (String key : scalarMap.keySet()) {
									query.addScalar(key, scalarMap.get(key));
								}
							}
							query.setResultTransformer(resultTransformer);
							items = query.list();
						}
						PaginationSupport ps = new PaginationSupport(items,
								totalVal, maxResults, firstResults);
						return ps;
					}
				});
	}

	public List findSQL(final String sql, final Object param,
			final Map<String, NullableType> scalarMap,
			final ResultTransformer resultTransformer, final int firstResults,
			final int maxResults) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				addParameters(query, param);
				if (maxResults > 0) {
					query.setFirstResult(firstResults);
					query.setMaxResults(maxResults);
				}
				if (MapUtils.isNotEmpty(scalarMap)) {
					for (String key : scalarMap.keySet()) {
						query.addScalar(key, scalarMap.get(key));
					}
				}
				query.setResultTransformer(resultTransformer);
				return query.list();
			}
		});
	}

	public PaginationSupport getSQLPaginationOnTables(
			final List<String> queryTableNames, final String sql,
			final String countSql, final String orderbySql, final Object param,
			final Map<String, NullableType> scalarMap,
			final ResultTransformer resultTransformer, final int page,
			final int max, final boolean cacheable) {
		return (PaginationSupport) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						List items = new ArrayList();
						List<Integer> totals = new ArrayList<Integer>();

						int total = 0;
						int currentPage = page;
						if (currentPage < 1)
							currentPage = 1;

						StringBuilder querySql = new StringBuilder();
						final int unionMax = 5;
						int tableSize = queryTableNames.size();
						int queryStartIndex = 0;// 查询数据起始索引
						int firstQueryResultStartIndex = 0;// 首次查询列表数据起始索引

						for (int i = 0; i < tableSize; i++) {
							// 查询结果总数，用于分页
							String queryTableName = queryTableNames.get(i);
							StringBuilder singleCountSql = new StringBuilder();
							singleCountSql
									.append("select count(*) as total from ")
									.append(queryTableName).append(countSql);

							querySql.append(singleCountSql);
							if ((i + 1) % unionMax == 0 || i == tableSize - 1) {
								if (querySql.indexOf("union") != -1) {
									querySql.append(")");
								}
								// 执行SQL
								SQLQuery query = s.createSQLQuery(querySql
										.toString());
								query.addScalar("total", Hibernate.INTEGER);

								List<Integer> list = query.list();
								boolean isDataEnough = false;
								for (Integer qTotal : list) {
									total += qTotal;
									totals.add(qTotal);// TODO 可能会丢索引

									if (total >= (currentPage + 1) * max) {
										isDataEnough = true;
										break;
									} else {
										if (currentPage > 1
												&& queryStartIndex == 0) {// 非首页，获取查询开始索引及首次查询列表数据起始索引
											if (total > (currentPage - 1) * max) {
												queryStartIndex = totals.size() - 1;
												firstQueryResultStartIndex = qTotal
														+ (currentPage - 1)
														* max - total;
											} else if (total == (currentPage - 1)
													* max) {
												queryStartIndex = totals.size();
											}
											if (queryStartIndex < 0)
												queryStartIndex = 0;
										}
									}

								}
								// 数据足够的总数，退出循环
								if (isDataEnough)
									break;

								querySql = new StringBuilder();
							} else {
								if (querySql.indexOf("union") == -1) {
									querySql.insert(0, "(");
								}
								querySql.append(") union all (");
							}

						}
						logger.info("queryTableNames : " + queryTableNames
								+ ", totals : " + totals + ", total : " + total);

						if (total > 0) {
							// 查询结果总数大于0，需要查询详细数据
							tableSize = totals.size();
							int maxPage = PaginationUtils
									.getMaxPage(total, max);
							currentPage = PaginationUtils.getRightPage(maxPage,
									page);

							querySql = new StringBuilder();
							for (int i = queryStartIndex; i < tableSize; i++) {
								if (totals.get(i) == 0) {
									// 本次数据总数为0，无需执行数据列表查询，跳过
									continue;
								}

								String queryTableName = queryTableNames.get(i);

								querySql.append(sql.replace(
										"$(queryTableName)", queryTableName));
								if ((i + 1) % unionMax == 0
										|| i == tableSize - 1) {
									if (querySql.indexOf("union") != -1) {
										querySql.append(")");
									}
									int maxResults = 0;
									if (currentPage == 1 && (i + 1) == unionMax) {
										// 首页且首次，设置max查询
										maxResults = max;
									}
									List list = getQueryResult(s,
											querySql.toString(), orderbySql,
											scalarMap, resultTransformer,
											maxResults, true);
									items.addAll(list);
									querySql = new StringBuilder();
								} else {
									if (querySql.indexOf("union") == -1) {
										querySql.insert(0, "(");
									}
									querySql.append(") union all (");
								}
							}
						}
						// 遗漏sql未执行，这里执行
						if (querySql.toString().endsWith(") union all (")) {
							querySql.delete(
									querySql.lastIndexOf(") union all (") + 1,
									querySql.length());
							if (querySql.indexOf("union") == -1) {
								querySql.deleteCharAt(0);
								querySql.deleteCharAt(querySql.length() - 1);
							}
							List list = getQueryResult(s, querySql.toString(),
									orderbySql, scalarMap, resultTransformer,
									max, true);
							items.addAll(list);
							querySql = null;
						}
						// 首次查询记录列表起始索引处理
						if (firstQueryResultStartIndex > 0) {
							items = items.subList(firstQueryResultStartIndex,
									items.size());
						}
						if (items.size() > max)
							items = items.subList(0, max);

						PaginationSupport ps = new PaginationSupport(items,
								total, max, (currentPage - 1) * max);
						return ps;
					}
				});
	}

	public List findHQL(final String hql, final Object param,
			final int maxResults) {
		return findHQL(hql, param, 0, maxResults);
	}

	public List findHQL(final String hql, final Object param,
			final int firstResults, final int maxResults) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				Query query = s.createQuery(hql);
				addParameters(query, param);
				if (maxResults > 0) {
					query.setFirstResult(firstResults);
					query.setMaxResults(maxResults);
				}
				return query.list();
			}
		});
	}

	public List findSQL(final String sql, final Object param,
			final Map<String, Class> entityMap, final int maxResults) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				addParameters(query, param);
				if (maxResults > 0) {
					query.setMaxResults(maxResults);
				}
				if (MapUtils.isNotEmpty(entityMap)) {
					for (String key : entityMap.keySet()) {
						query.addEntity(key, entityMap.get(key));
					}
				}
				return query.list();
			}
		});
	}

	public List findSQL(final String sql, final Object param,
			final Map<String, Class> entityMap) {
		return findSQL(sql, param, entityMap, 0);
	}

	/**
	 * 获取查询结果
	 * 
	 * @param s
	 * @param sql
	 * @param orderbySql
	 * @param scalarMap
	 * @param resultTransformer
	 * @param max
	 * @param cacheable
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 */
	private List getQueryResult(Session s, String sql, String orderbySql,
			Map<String, NullableType> scalarMap,
			ResultTransformer resultTransformer, int max, boolean cacheable)
			throws HibernateException, SQLException {
		// 执行SQL
		SQLQuery query = s.createSQLQuery(sql + orderbySql);
		if (max > 0) {
			// 设置max查询
			query.setMaxResults(max);
		}
		if (cacheable) {
			// 设置是否缓存
			query.setCacheable(true);
		}

		for (String key : scalarMap.keySet()) {
			query.addScalar(key, scalarMap.get(key));
		}
		query.setResultTransformer(resultTransformer);
		return query.list();
	}

	@Override
	public int executeUpdate(Class clazz, Map<String, Object> setParams,
			String whereHql, List whereParamVals) {
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder("update "
				+ clazz.getName());

		hql.append(" set ");
		for (String key : setParams.keySet()) {
			hql.append(key).append("=?,");
			params.add(setParams.get(key));
		}
		hql.deleteCharAt(hql.length() - 1);

		if (StringUtils.isNotEmpty(whereHql)) {
			hql.append(whereHql);
			params.addAll(whereParamVals);
		}

		return getHibernateTemplate().bulkUpdate(hql.toString(),
				params.toArray(new Object[0]));

	}

	public static void main(String[] args) {

		String hql = "select a,sqrt(power((a.longitude-116.407413)*102834.74258026089786013677476285,2)+power((a.latitude-39.904214)*111712.69150641055729984301412873,2)) as distance from User a where a.freeze = 1 order by sqrt(power((a.longitude-116.407413)*102834.74258026089786013677476285,2)+power((a.latitude-39.904214)*111712.69150641055729984301412873,2))";
		hql = new GenericHibernateDAO().addCount(hql);
		hql = new GenericHibernateDAO().delOrder(hql);
		System.out.println(hql);

	}

}
