<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.LoginInfo" %>
<%@ page import="com.sw.cms.util.*" %><%
LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
if(info == null){
	out.print(0);
	return;
}
String user_id = info.getUser_id();
String noMsgNums = "0";
Map userNoReadMsgMap = (Map)application.getAttribute("userNoReadMsgMap");
if(userNoReadMsgMap != null){
	noMsgNums = StringUtils.nullToStr(userNoReadMsgMap.get(user_id));
	if(noMsgNums.equals("")){
		noMsgNums = "0";
	}
}
out.print(noMsgNums);
%>