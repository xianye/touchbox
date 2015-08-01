package com.xc.touchbox.model.view;

import java.io.Serializable;

public class LoginResponse extends BaseResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3642472673875538204L;

	private AppVersionUpdate versionUpdate;// 版本更新对象（app专有）
	private AccountView account;// 账号对象

	public AppVersionUpdate getVersionUpdate() {
		return versionUpdate;
	}

	public void setVersionUpdate(AppVersionUpdate versionUpdate) {
		this.versionUpdate = versionUpdate;
	}

	public AccountView getAccount() {
		return account;
	}

	public void setAccount(AccountView account) {
		this.account = account;
	}

}
