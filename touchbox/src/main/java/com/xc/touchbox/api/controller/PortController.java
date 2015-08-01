package com.xc.touchbox.api.controller;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.xc.touchbox.controller.BaseAction;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/")
@Results({ @Result(name = "2000", type = "chain", location = "query") })
public class PortController extends BaseAction {

	@Action(value = "port")
	public String port() {
		return String.valueOf(c);
	}
}
