<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.service.ClientsService" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("clientsPage");
ClientsService clientsService = (ClientsService)VS.findValue("clientsService");

String name = ParameterUtility.getStringParameter(request,"name", "");
String type = ParameterUtility.getStringParameter(request,"type", "");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>往来单位</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function sel(id,name){
		var c_id = window.opener.document.getElementById("client_id");
		var c_name = window.opener.document.getElementById("client_name");
		
		c_id.value = id;
		c_name.value = name;
		
		//window.opener.document.XsskForm.action = "addXssk.html";
		//window.opener.document.XsskForm.submit();
		window.opener.queryYszd();
		
		window.close();
	}
	
	function clearAll(){
		document.getElementById("name").value = "";	
	}
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="selXsskClient.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			往来单位：<input type="text" name="name" value="<%=name %>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center" border="1"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>编号</td>
		<td>单位名称</td>
		<td>联系人</td>
		<td>联系电话</td>
		<td>手机</td>
		<td>限额</td>
		<td>应收款</td>
		<td>应付款</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();

		double xe = map.get("xe")==null?0:((Double)map.get("xe")).doubleValue();
		
		double yinshouje = clientsService.getClientYinshou(StringUtils.nullToStr(map.get("id")));
		double yinfuje = clientsService.getClientYinfu(StringUtils.nullToStr(map.get("id")));
	%>
	<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="左键点击选择" onclick="sel('<%=StringUtils.nullToStr(map.get("id")) %>','<%=StringUtils.nullToStr(map.get("name")) %>');">
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("lxr")) %></td>
		<td><%=StringUtils.nullToStr(map.get("lxdh")) %></td>
		<td><%=StringUtils.nullToStr(map.get("mobile")) %></td>
		<td><%=JMath.round(xe,2) %></td>
		
		<td><%=JMath.round(yinshouje,2) %></td>
		<td><%=JMath.round(yinfuje,2) %></td>
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
