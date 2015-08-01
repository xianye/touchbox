package com.xc.touchbox.model.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.xc.gospel.core.vo.PaginationSupport;

public class PaginationResponse<T> extends BaseResponse implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8872942184087923116L;

	private int total;
	private int page;
	private int pagesize;
	private int maxpage;
	private List<T> list;

	public PaginationResponse() {
	}

	public PaginationResponse(PaginationSupport ps) {
		this(ps, null);
	}

	/**
	 * PaginationSupport转为PaginationResponse对象
	 * 
	 * @param ps
	 *            分页对象
	 * @param itemClass
	 *            分页数据Java类型
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public PaginationResponse(PaginationSupport ps, Class itemClass) {
		this.total = ps.getTotalCount();
		this.page = ps.getStartIndex() / ps.getPageSize() + 1;
		this.pagesize = ps.getPageSize();
		this.maxpage = ps.getIndexes().length;
		List targetList = ps.getItems();

		if (itemClass != null && CollectionUtils.isNotEmpty(ps.getItems())) {
			targetList = new ArrayList();
			try {
				for (Object source : ps.getItems()) {
					Object target = itemClass.newInstance();
					BeanUtils.copyProperties(source, target);
					targetList.add(target);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.list = targetList == null ? new ArrayList() : targetList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getMaxpage() {
		return maxpage;
	}

	public void setMaxpage(int maxpage) {
		this.maxpage = maxpage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getErrorCode() {
		return super.getErrorCode();
	}

	public String getErrorMsg() {
		return super.getErrorMsg();
	}
}
