<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sw.cms.jfreechar.*" %>
<%@ page import = "java.io.PrintWriter" %> 
<%
TestFreeCharImp chart = new TestFreeCharImp();
String filename = chart.getPieChart3D("饼状图测试",session,new PrintWriter(out));
String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;

String filename2 = chart.getBarChart3D("柱状图测试","","",session,new PrintWriter(out));
String graphURL2 = request.getContextPath() + "/DisplayChart?filename=" + filename2;

String filename3 = chart.getLineChart("折线图测试","","",session,new PrintWriter(out));
String graphURL3 = request.getContextPath() + "/DisplayChart?filename=" + filename3;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JFreeChar图形测试</title>
</head>
<body>
<img src="<%=graphURL%>" border=0 usemap="<%=filename %>"><BR><BR>
<img src="<%=graphURL2%>" border=0 usemap="<%=filename2 %>"><BR><BR>
<img src="<%=graphURL3%>" border=0 usemap="<%=filename3 %>">
</body>
</html>