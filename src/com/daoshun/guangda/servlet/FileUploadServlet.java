package com.daoshun.guangda.servlet;

/**
 * 
 */

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangcl
 * 
 */
@WebServlet("/fileupload")
public class FileUploadServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			getAction(request);
			resultMap.put("fileid", getRequestParamter(request, "file"));
			setResult(response, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
