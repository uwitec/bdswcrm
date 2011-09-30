<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.sw.cms.model.LoginInfo" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %><%
int onlineNums = 0;
List onlineList = (List)application.getAttribute("onlineUserList");
if(onlineList != null){
	onlineNums = onlineList.size();
}
out.print(onlineNums);
%>