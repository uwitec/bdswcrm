<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List user_list = (List)VS.findValue("user_list");
%>

<html>
<head>
<title>货品销售毛利汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function openWin(){
		var destination = "selKind.html";
		var fea ='width=400,height=500,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择商品类别',fea);
	}	
</script>
</head>
<body  >
<form name="reportForm" action="getHpxsMlHzResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">货品销售毛利汇总</td>
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
		<input type="text" name="clientId" id="client_name" value="" readonly size="30" onclick="openClientWin();" maxlength="50">
		<input type="hidden" name="clientName" id="client_id" value="">
		<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openClientWin();" style="cursor:hand">
		</td>	
		<td class="a1">销售人员</td>
		<td class="a4">
			<select name="xsry_id">
				<option value=""></option>
				<%
				if(user_list != null &&  user_list.size()>0){
					for(int i=0;i<user_list.size();i++){
						Map map = (Map)user_list.get(i);
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
		<td class="a1" width="15%">商品类别</td>
		<td class="a4" width="35%">
			<input type="text" name="kind_name" id="kind_name" value="" size="55" onclick="openWin();" readonly>
			<input type="hidden" name="product_kind" id="product_kind" value="">
			<img src="images/select.gif" align="absmiddle" title="点击选择类别" border="0" onclick="openWin();" style="cursor:hand">
		</td>
		<td class="a1" width="15%">商品名称</td>
		<td class="a4" width="35%">
			<input type="text" name="product_name" id="product_name" value="">
		</td>										
	</tr>
	<tr>
		<td class="a1" width="15%">商品规格</td>
		<td class="a4" colspan="3">
			<input type="text" name="product_xh" id="product_xh" value="">
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
