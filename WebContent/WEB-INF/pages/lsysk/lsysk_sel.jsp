<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("lsyskPage");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");

String id = (String)VS.findValue("id");
String client_name = (String)VS.findValue("client_name");
String ys_date1 = (String)VS.findValue("ys_date1");
String ys_date2 = (String)VS.findValue("ys_date2");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>零售预收款</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function clearAll(){
		document.myform.id.value = "";
		document.myform.client_name.value = "";
		document.myform.ys_date1.value = "";
		document.myform.ys_date2.value = "";
	}	
	
	function doSort(order_name){
		if(myform.orderType.value=='asc'){
			myform.orderType.value='desc';
		}else{
			myform.orderType.value='asc';	
		}
		myform.orderName.value = order_name;
	    myform.submit();		
	}
	
	function sel(id,yushkje,type){
		if(type == "已冲抵"){
			alert("该预收款已冲抵，请检查！");
			return;
		}
		
		var y_id = window.opener.document.getElementById("yushk_id");
		var y_je = window.opener.document.getElementById("yushkje");
		
		y_id.value = id;
		y_je.value = yushkje;
		
		window.opener.hj();
		
		window.close();
	}		
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="selLsysk.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>零售预收款</b></td>		
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;
			编号：<input type="text" name="id" value="<%=id %>" size="10">&nbsp;&nbsp;
			客户名称：<input type="text" name="client_name" value="<%=client_name %>">&nbsp;&nbsp;
			预收时间：<input type="text" name="ys_date1" value="<%=ys_date1 %>" size="12" class="Wdate" onFocus="WdatePicker()">
			&nbsp;至&nbsp;
			<input type="text" name="ys_date2" value="<%=ys_date2 %>" size="12" class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value="查询" class="css_button">
			<input type="button" name="buttonQk" value="清空" class="css_button" onclick="clearAll();">
		</td>				
	</tr>				
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1">
	<thead>
	<tr>
		<td onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">客户名称<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('jsr');">经手人<%if(orderName.equals("jsr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('ys_date');">预收日期<%if(orderName.equals("ys_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('ysje');">预收金额<%if(orderName.equals("ysje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('skzh');">收款账号<%if(orderName.equals("skzh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('type');">预收款状态<%if(orderName.equals("type")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
	</tr>
	</thead>
	<%
	Iterator its = (results.getResults()).iterator();
	
	while(its.hasNext()){
		Lsysk lsysk = (Lsysk)its.next();
		
	%>
	<tr class="a1"  title="左键双击选择" onDblClick="sel('<%=StringUtils.nullToStr(lsysk.getId()) %>','<%=JMath.round(lsysk.getYsje()) %>','<%=StringUtils.nullToStr(lsysk.getType()) %>');">
		<td><%=StringUtils.nullToStr(lsysk.getId()) %></td>
		<td><%=StringUtils.nullToStr(lsysk.getClient_name()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(lsysk.getJsr())) %></td>
		<td><%=StringUtils.nullToStr(lsysk.getYs_date()) %></td>
		<td><%=JMath.round(lsysk.getYsje(),2) %></td>
		<td><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(lsysk.getSkzh())) %></td>
		<td><%=StringUtils.nullToStr(lsysk.getType()) %></td>
	</tr>
	
	<%
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=results.getPageScript() %></td>
	</tr>
</table>
</form>
</body>
</html>
