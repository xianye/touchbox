package com.xc.touchbox.model.view;

import java.io.Serializable;

public class AppVersionUpdate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8293178873210327414L;
	private int updateable = 0;// 是否有新版本,0表示无,1表示有
	private String version;// 新版本号，如：1.1
	private String prompt;// 新版本提示语(有新版本时才出现)
	private String updateUrl;// 新版本下载URL(有新版本时才出现)

	public int getUpdateable() {
		return updateable;
	}

	public void setUpdateable(int updateable) {
		this.updateable = updateable;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}

}
