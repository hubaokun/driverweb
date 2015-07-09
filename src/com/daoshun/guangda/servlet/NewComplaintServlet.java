package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.ComplaintInfo;
import com.daoshun.guangda.service.ISOrderService;

@WebServlet("/complaint")
public class NewComplaintServlet extends BaseServlet {

	private static final long serialVersionUID = 6998440419757851253L;
	private ISOrderService sorderService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sorderService = (ISOrderService) applicationContext.getBean("sorderService");
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		request.setCharacterEncoding("utf-8");
		String polltime = request.getParameter("polltime");
		if (CommonUtils.isEmptyString(polltime)) {
			resultMap.put("pollflag", "0");
		} else {
			List<ComplaintInfo> complaintlist = sorderService.getComplaintByTime(polltime);
			if (complaintlist != null && complaintlist.size() > 0) {
				resultMap.put("pollflag", "1");
			}
		}
		resultMap.put("polltime", CommonUtils.getCurrentTimeFormat(""));
		request.getSession().setAttribute("polltime", CommonUtils.getCurrentTimeFormat(""));
		super.setResult(response, resultMap);
	}

}
