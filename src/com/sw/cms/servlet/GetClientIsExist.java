package com.sw.cms.servlet;

 

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sw.cms.service.DwrService;

public class GetClientIsExist extends HttpServlet
{
   public void service(HttpServletRequest request,HttpServletResponse response)throws ServletException ,java.io.IOException
   {
	   DwrService dwrService=(DwrService) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("dwrService");
	   request.setCharacterEncoding("utf-8");
	   String client_name=null!=request.getParameter("client_name")?request.getParameter("client_name"):null;
	   Map map=(Map)dwrService.getClientByClientName(client_name);
	   response.setContentType("text/html;charset=GBK");
	   PrintWriter out=response.getWriter();
	   String name="0";
	   if(null!=map.get("id").toString())
	   {
		   
		   out.println(name=map.get("id").toString());
	   }
	   else
	   {
		   out.println(name);
	   }
   }
}
