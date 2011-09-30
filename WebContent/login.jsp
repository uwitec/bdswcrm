<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.sw.cms.util.*" %>
<%
session.removeAttribute("LOGINUSER");
response.sendRedirect(request.getContextPath() + "/toLogin.html");
%>