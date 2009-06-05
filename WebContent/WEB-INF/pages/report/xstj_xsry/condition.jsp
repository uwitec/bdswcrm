<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
%>

<html>
<head>
<title>业务员销售汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
</head>
<body  >
<form name="reportForm" action="getXstjXsryResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">业务员销售汇总</td>
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
			<input type="text" name="client_name" id="client_name" value="">
		</td>	
		<td class="a1">显示产品明细</td>
		<td class="a4">
			<input type="radio" name="flag" id="flag" value="1"> 是
			<input type="radio" name="flag" id="flag" value="0" checked> 否
		</td>		
	</tr>
	<tr>
		<td class="a1">销售人员</td>
		<td class="a4">
			<select name="xsry_id">
				<option value="">----请选择----</option>
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
		<td class="a1">业务类型</td>
		<td class="a4">
			<input type="checkbox" name="xwType" id="xwType" value="1" checked> 销售订单
			<input type="checkbox" name="xwType" id="xwType" value="2" checked> 零售单
			<input type="checkbox" name="xwType" id="xwType" value="3" checked> 退货单
		</td>				
	</tr>	
	<tr>
		<td class="a1">单据编号</td>
		<td class="a4">
			<input type="text" name="dj_id" id="dj_id" value="" size="20">
		</td>
		<td class="a1">显示销量为0业务员</td>
		<td class="a4">
			<select name="isShowZ">
				<option value="否">否</option>
				<option value="是">是</option>
			</select>
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
