package com.xc.touchbox.model.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

public class ListResponse extends BaseResponse implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4393948236402252231L;

	private int total = 0;
	private List list = new ArrayList();

	public ListResponse() {
	}

	/**
	 * items 数据转换为 view对象列表
	 * 
	 * @param items
	 * @param itemClass
	 */
	public ListResponse(List items, Class itemClass) {
		if (CollectionUtils.isNotEmpty(items)) {
			this.total = items.size();
			List targetList = items;
			if (itemClass != null) {
				targetList = new ArrayList();
				try {
					for (Object source : items) {
						Object target = itemClass.newInstance();
						BeanUtils.copyProperties(source, target);
						targetList.add(target);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.list = targetList;
		}
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getErrorCode() {
		return super.getErrorCode();
	}

	public String getErrorMsg() {
		return super.getErrorMsg();
	}

}
