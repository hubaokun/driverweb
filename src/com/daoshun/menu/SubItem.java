/**
 * 
 */
package com.daoshun.menu;

import org.dom4j.Element;

import com.daoshun.common.CommonUtils;

/**
 * @author qiuch
 * 
 */
public class SubItem {

	private String name;
	private String action;
	private int authority;

	public SubItem(Element subItem) {
		name = subItem.attributeValue("name");
		action = subItem.attributeValue("action");
		authority = CommonUtils.parseInt(subItem.attributeValue("authority"), 0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}
}
