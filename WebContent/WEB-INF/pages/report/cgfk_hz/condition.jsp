<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
List accountList = (List)VS.findValue("accountList");
%>
<html>
<head>
<title>采购付款汇总统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
</head>
<body  >
<form name="reportForm" action="getCgfkHzResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">采购付款汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('start_date')); return false;"></td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('end_date')); return false;"></td>
	</tr>
	<tr>
		<td class="a1">客户名称</td>
		<td class="a4">
			<input type="text" name="client_name" id="client_name" value="" size="20">
		</td>
		<td class="a1">经手人</td>
		<td class="a4">
			<select name="jsr">
				<option value="">----请选择-----</option>
				<%
				if(userList != null &&  userList.size()>0){
					for(int i=0;i<userList.size();i++){
						Map map = (Map)userList.get(i);
						String real_name = StringUtils.nullToStr(map.get("real_name"));
						String user_id = StringUtils.nullToStr(map.get("user_id"));
				%>
				<option value="<%=user_id %>"><%=real_name %></option>
				<%
					}
				}
				%>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="a1">付款账户</td>
		<td class="a4">
			<select name="account_id">
				<option value="">----请选择-----</option>
				<%
				if(accountList != null &&  accountList.size()>0){
					for(int i=0;i<accountList.size();i++){
						Map map = (Map)accountList.get(i);
						String name = StringUtils.nullToStr(map.get("name"));
						String id = StringUtils.nullToStr(map.get("id"));
				%>
				<option value="<%=id %>"><%=name %></option>
				<%
					}
				}
				%>
			</select>
		</td>	
		<td class="a1">包括预付</td>
		<td class="a4">
			<input type="radio" name="isYfk" id="isYfk" value="1" checked> 是
			<input type="radio" name="isYfk" id="isYfk" value="2"> 否
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="submit" name="button1" value="提 交" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>
