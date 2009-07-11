<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %><%@ page import="com.sw.cms.util.*" %><%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %><%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String clientsTips = StringUtils.nullToStr(VS.findValue("clientsTips"));
out.println(clientsTips);
%>