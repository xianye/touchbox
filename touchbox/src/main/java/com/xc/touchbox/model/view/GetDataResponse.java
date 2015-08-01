package com.xc.touchbox.model.view;

import org.springframework.beans.BeanUtils;

public class GetDataResponse extends BaseResponse implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3652770785621277052L;

	private Object data = null;

	public GetDataResponse() {
	}

	/**
	 * items 数据转换为 view对象列表
	 * 
	 * @param items
	 * @param itemClass
	 */
	public GetDataResponse(Object item, Class itemClass) {
		if (item != null) {
			Object target = item;
			if (itemClass != null) {
				try {
					Object target1 = itemClass.newInstance();
					BeanUtils.copyProperties(item, target1);
					target = target1;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.data = target;
		}
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getErrorCode() {
		return super.getErrorCode();
	}

	public String getErrorMsg() {
		return super.getErrorMsg();
	}

}
