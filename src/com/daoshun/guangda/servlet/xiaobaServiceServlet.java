package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoshun.common.Constant;


/**
 * 
 * @author wjr
 *
 */
@WebServlet("/xbservice")
public class xiaobaServiceServlet extends BaseServlet{
	    @Override
	    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	    {
		 HashMap<String, Object> resultMap = new HashMap<String, Object>();
		    	String action=getAction(request);
		    	
		    	if(action.equals(Constant.XIAOBASERVICE))
		    	{
		    		resultMap.put("simulatetraining", "https://jinshuju.net/f/VC2AGm");
		    		resultMap.put("bookexam", "https://jinshuju.net/f/N3jDXw");
		    	}
	    	setResult(response, resultMap);
	    }
}
