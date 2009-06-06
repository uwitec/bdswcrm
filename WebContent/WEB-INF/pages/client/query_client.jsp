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
%>

<html>
<head>
<title>客户查询--列表信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">	
	function openWin(id){
		var destination = "viewClient.html?id="+id;
		window.open(destination);	
	}	
</script>
</head>
<body>
<form name="myform" action="queryClients.html" method="post">
<table width="100%"  align="center" border="1"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>客户编号</td>
		<td>客户名称</td>
		<td>联系电话</td>
		<td>限额</td>
		<td>应收款</td>
		<td>应付款</td>
		<td>客户经理</td>
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
	<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="双击查看详情"  onDblClick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');">
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("lxdh")) %></td>
		<td><%=JMath.round(xe,2) %></td>
		<td><%=JMath.round(yinshouje,2) %></td>
		<td><%=JMath.round(yinfuje,2) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("khjl"))) %></td>		
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
