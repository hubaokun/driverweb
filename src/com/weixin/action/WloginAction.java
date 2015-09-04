package com.weixin.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.action.BaseAction;
import com.daoshun.guangda.pojo.RecommendInfo;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class WloginAction extends BaseAction {
	//results = { @Result(name = SUCCESS, location = "/recommendlist.jsp") }
	@Action(value = "/test")
	public void test()
	{
		//return SUCCESS;
	}
}
