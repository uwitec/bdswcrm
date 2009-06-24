package com.sw.cms.servlet;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sw.cms.service.DwrService;

public class GetUserAllServlet extends HttpServlet 
{ 
   public void service(HttpServletRequest request ,HttpServletResponse response)throws ServletException ,java.io.IOException
   {
	    
	   DwrService dwrService=(DwrService) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("dwrService");
	   request.setCharacterEncoding("utf-8");
	   String param=request.getParameter("prefix");
	    
	   List list=dwrService.getAllUserList(param);
	   List list1=(List)list.get(0);
	   List list2=(List)list.get(1);
	   String username="";
	   String userid="";
	   for(int i=0;i<list1.size();i++)
	   {
		   username+=list1.get(i)+"$";
	   }
	   for(int i=0;i<list2.size();i++)
	   {
		   userid+=list2.get(i)+"$";
	   }
	   
	   response.setContentType("text/html;charset=GBK");
	   PrintWriter out=response.getWriter();
	   out.println( username+"%"+userid);  
	   
   }
}
