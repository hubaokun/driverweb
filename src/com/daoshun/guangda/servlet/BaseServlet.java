/**
 * 
 */
package com.daoshun.guangda.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.ImageUtil;
import com.daoshun.guangda.service.IBaseService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * @author wangcl
 * 
 */
public class BaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ApplicationContext applicationContext;
	IBaseService baseService;
	private Map<String, Object> paramMap = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		baseService = (IBaseService) applicationContext.getBean("baseService");
	}

	/**
	 * Servlet 返回结果
	 * 
	 * @param HttpServletResponse
	 */
	public void setResult(HttpServletResponse response, HashMap<String, Object> resultMap) {
		response.setContentType("text/html;charset=UTF-8");
		if (resultMap.get("code") == null) {
			resultMap.put(Constant.CODE, 1);
			resultMap.put(Constant.MESSAGE, "操作成功");
		}
		try {
			PrintWriter out = response.getWriter();
			// json格式转换
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().create();
			String json = gson.toJson(resultMap);
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			resultMap.clear();
		}
	}
	/**
	 * Servlet 返回结果
	 * 
	 * @param HttpServletResponse
	 */
	/*public void setAliPayResult(HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.print("success");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	public void setResultWhenException(HttpServletResponse response, String errmsg) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(errmsg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String getAction(HttpServletRequest request) {
		String action = null;
		// 上传文件的场合
		if (ServletFileUpload.isMultipartContent(request)) {
			// 设定文件上传的路径
			String localPath = CommonUtils.getLocationPath() + CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
			CommonUtils.checkPath(localPath);
			// 获得磁盘文件工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			try {
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					// 文件名称取得
					String fieldname = item.getFieldName();
					final String uploadfilename = item.getFieldName();
					if (item.isFormField()) {
						// 普通文本信息处理
						String value = item.getString("UTF-8");
						request.setAttribute(fieldname, value);
					} else {
						// 上传文件
						if (fieldname.indexOf(".") == -1) {
							fieldname += ".jpg";
						}
						String extension = fieldname.substring(fieldname.lastIndexOf(".")).toLowerCase();
						// 图片存放的名字 时间戳加图片后缀名
						String filename = CommonUtils.getTimeFormat(new Date(), "hhmmssSSS") + "_" + (int) (Math.random() * 100) + "origin" + extension;
						item.write(new File(localPath + filename));
						long fileid = 0;
						// 压缩图片
						if (ImageUtil.isImage(filename)) {
							String filenameEX = filename.replace("origin", "");
							ImageUtil.scale4(localPath + filename, localPath + filenameEX, 999999999, 300);
							fileid = baseService.uploadComplete(localPath.replace(CommonUtils.getLocationPath(), ""), filenameEX);
						} else {
							fileid = baseService.uploadComplete(localPath.replace(CommonUtils.getLocationPath(), ""), filename);
						}
						// System.out.println(fieldname.lastIndexOf("."));
						request.setAttribute(uploadfilename, fileid + "");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// action取得
		action = getRequestParamter(request, "action");
		if (action != null) {
			action = action.toUpperCase();
		} else {
			try {
				paramMap = new Gson().fromJson(new InputStreamReader(request.getInputStream(), "utf-8"), new TypeToken<Map<String, Object>>() {
				}.getType());
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (paramMap != null) {
				Iterator<Entry<String, Object>> iterator = paramMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> entry = iterator.next();
					String key = entry.getKey();
					if (key.equals("action")) {
						action = String.valueOf(entry.getValue()).toUpperCase();
					} else {
						request.setAttribute(key, entry.getValue());
					}
				}
				paramMap = null;
			}
		}
		return action;
	}

	public String getRequestParamter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (value == null) {
			value = (String) request.getAttribute(name);
		}
		return value;
	}
}
