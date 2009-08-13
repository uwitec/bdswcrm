<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sw.cms.jfreechar.*" %>
<%@ page import = "java.io.PrintWriter" %> 
<%
TestFreeCharImp chart = new TestFreeCharImp();
String filename = chart.getPieChart3D("Test",session,new PrintWriter(out));
String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;

String filename2 = chart.getBarChart3D("柱状图测试","x","y",session,new PrintWriter(out));
String graphURL2 = request.getContextPath() + "/DisplayChart?filename=" + filename2;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<img src="<%=graphURL%>" border=0>
<img src="<%=graphURL2%>" border=0>
</body>
</html>