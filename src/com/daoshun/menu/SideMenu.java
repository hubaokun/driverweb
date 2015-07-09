/**
 * 
 */
package com.daoshun.menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.daoshun.common.CommonUtils;

/**
 * @author qiuch
 * 
 */
public class SideMenu {

	private static String appname;
	private static String iconURL;
	private static int authorityType;
	private static List<MenuItem> menuItems;
	private static SideMenu instance;

	public SideMenu() {
		// init(System.getProperty("user.dir") + File.separator + "src" + File.separator + "menu.xml");
		CommonUtils.getFileRootUrl();
		init("");
	}

	public SideMenu(String configPath) {
		init(configPath);
	}

	public static SideMenu getInstance() {
		if (instance == null) {
			instance = new SideMenu();
		}
		return instance;
	}

	private void init(String configPath) {
		InputStream stream = SideMenu.class.getClassLoader().getResourceAsStream("menu.xml");
		try {
//			stream = new FileInputStream(configPath);
			stream = SideMenu.class.getClassLoader().getResourceAsStream("menu.xml");
			SAXReader saxReader = new SAXReader();
			Document document;
			document = saxReader.read(stream);
			// 获取根元素
			Element menuList = document.getRootElement();
			appname = menuList.attributeValue("appname");
			iconURL = menuList.attributeValue("iconURL");
			authorityType = CommonUtils.parseInt(menuList.attributeValue("authorityType"), 0);
			// 获取所有子元素
			List<Element> childList = menuList.elements("menu-item");
			menuItems = new ArrayList<MenuItem>();
			for (Element element : childList) {
				menuItems.add(new MenuItem(element));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				stream = null;
			}
		}
	}

	public static String getAppname() {
		return appname;
	}

	public static void setAppname(String appname) {
		SideMenu.appname = appname;
	}

	public static String getIconURL() {
		return iconURL;
	}

	public static void setIconURL(String iconURL) {
		SideMenu.iconURL = iconURL;
	}

	public static int getAuthorityType() {
		return authorityType;
	}

	public static void setAuthorityType(int authorityType) {
		SideMenu.authorityType = authorityType;
	}

	public static List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public static void setMenuItems(List<MenuItem> menuItems) {
		SideMenu.menuItems = menuItems;
	}

	public static void setInstance(SideMenu instance) {
		SideMenu.instance = instance;
	}

	@Override
	public String toString() {
		return "SideMenu [appname=" + appname + ", iconURL=" + iconURL + ", authorityType=" + authorityType + ", menuItems=" + menuItems + "]";
	}
}
