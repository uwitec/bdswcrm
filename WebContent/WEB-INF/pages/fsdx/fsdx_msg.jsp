<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List loglist=(List)VS.findValue("loglist");
request.setAttribute("loglist",loglist);

%>
<script>
	alert("发送完毕！");
	parent.window.close();
      
          
		var destination = "send_result.jsp";
		var fea = 'width=400,height=300,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'发送日志',fea);		
			
</script>