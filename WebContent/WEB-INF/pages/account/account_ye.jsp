<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List accountList = (List)VS.findValue("accountList");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账户余额</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="listAccount.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>账户余额</b></td>		
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>编号</td>
		<td>名称</td>
		<td>账户类型</td>
		<td>开户行</td>
		<td>账号</td>
		<td>期初金额</td>
		<td>当前金额</td>
	</tr>
	</thead>
	<%
	Iterator its = accountList.iterator();
	
	while(its.hasNext()){
		Map map = (Map)its.next();
		
		double qcje = map.get("qcje")==null?0:((Double)map.get("qcje")).doubleValue();
		double dqje = map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue();
	%>
	<tr class="a1">
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("type")) %></td>
		<td><%=StringUtils.nullToStr(map.get("bank")) %></td>
		<td><%=StringUtils.nullToStr(map.get("bank_count")) %></td>
		<td><%=JMath.round(qcje,2) %></td>
		<td><%=JMath.round(dqje,2) %></td>
	</tr>
	
	<%
	}
	%>
</table>
</form>
</body>
</html>
