/*
 * Created on 2004-11-25
 *
 */
package com.xc.gospel.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 唐俊
 * @version 1.11
 * 
 */
public class PaginationUtils {
	/**
	 * 提供私有空构造方法使得工具类不能实例化
	 * 
	 */
	private PaginationUtils() {
	}

	/**
	 * 
	 * 按照所得对象总数及max得到所分的最大页数，必须大于等于零
	 * 
	 * @param count
	 *            所得对象总数，必须大于零
	 * @param max
	 *            一页显示的行数即对象数，必须大于零
	 * @return 最大页数
	 */
	public static int getMaxPage(int count, int max) {
		if (count <= 1) {
			return 1;
		}
		if ((count % max) == 0) {
			return count / max;
		}
		return (count / max) + 1;
	}

	/**
	 * 
	 * 按照所得对象列表及max得到所分的最大页数，必须大于等于零
	 * 
	 * @param list
	 *            所得的对象列表java.util.List
	 * @param max
	 *            一页显示的行数即对象数，必须大于零
	 * @return 最大页数
	 */
	public static int getMaxPage(List list, int max) {
		int count;

		if ((list == null) || list.isEmpty()) {
			throw new IllegalArgumentException("输入的参数list为空或为null值");
		}
		count = list.size();
		return getMaxPage(count, max);
	}

	/**
	 * <pre>
	 * 根据当前页数及最大页数取得正确的页数,必须大于零<br>
	 * 取1到maxpage最大页数中的一个值，即取大于等于1，小于等于maxpage的一个数
	 * </pre>
	 * 
	 * @param maxpage
	 *            最大页数,必须大于零
	 * @param page
	 *            当前页码,必须是整数
	 * @return 正确的页码
	 */
	public static int getRightPage(int maxpage, int page) {
		if (page < 1) {
			return 1;
		}
		if (page > maxpage) {
			return maxpage;
		}
		return page;
	}

	/**
	 * 
	 * 根据当前页数、所得对象总数及max取得正确的页数,必须大于零
	 * 
	 * @param count
	 *            所得对象总数，必须大于零
	 * @param page
	 *            当前页码,必须是整数
	 * @param max
	 *            一页显示的行数即对象数，必须大于零
	 * @return 正确的页码
	 */
	public static int getRightPage(int count, int page, int max) {
		int maxpage = getMaxPage(count, max);
		return getRightPage(maxpage, page);
	}

	/**
	 * 
	 * 根据当前页、所得的对象列表及max取得正确的页码,必须大于零
	 * 
	 * @param list
	 *            所得的对象列表java.util.List
	 * @param page
	 *            当前页码,必须是整数
	 * @param max
	 *            一页显示的行数即对象数，必须大于零
	 * @return 正确的页码
	 */
	public static int getRightPage(List list, int page, int max) {
		int maxpage = getMaxPage(list, max);
		return getRightPage(maxpage, page);
	}

	/**
	 * 
	 * 根据content和size返回一个按照最大允许长度被分割的内容列表java.util.List
	 * 
	 * @param content
	 *            当前内容
	 * @param size
	 *            当前页允许的内容长度
	 * @return 一个按照最大允许长度被分割的内容列表java.util.List
	 */
	public static List getContentList(String content, int size) {
		List list;
		int page;
		if ((content == null) || (content.trim().length() == 0)) {
			throw new IllegalArgumentException("输入的参数content--" + content);
		}
		list = new ArrayList();
		page = content.length() / size;
		for (int i = 0; i < page; i++) {
			list.add(content.substring(i * size, (i + 1) * size));
		}
		if ((content.length() % size) != 0) {
			list.add(content.substring(page * size));
		}
		return list;
	}

	/**
	 * 
	 * 按照页码取得一页最大max个对象列表java.util.List
	 * 
	 * @param list
	 *            原java.util.List对象
	 * @param page
	 *            页码，从1开始，必须为整数
	 * @param order
	 *            true则顺序，false则反序，boolean类型值
	 * @param max
	 *            一页的最大行数，必须是大于0的整数
	 * @return 一页最大max个对象列表java.util.List
	 */
	public static List getSubList(List list, int page, boolean order, int max) {
		int intpage;
		if (list == null || list.isEmpty() || (max < 1)) {
			throw new IllegalArgumentException("输入的参数list为空或为null值或max小于1");
		}
		if (list.contains(null)) {
			list.remove(null);
		}
		if (!order) {
			Collections.reverse(list);
		}
		intpage = getRightPage(list, page, max);
		if ((max * intpage) > list.size()) {
			return list.subList((intpage - 1) * max, list.size());
		} else {
			return list.subList((intpage - 1) * max, intpage * max);
		}
	}
}
