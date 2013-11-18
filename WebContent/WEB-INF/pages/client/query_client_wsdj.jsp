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

Map qcMap = (Map)VS.findValue("clientQcMap");
Map wlMap = (Map)VS.findValue("clientWlMap");
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户视图</title>
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
<form name="myform" action="queryClients_wsdj.html" method="post">
<input type="hidden" name="client_name" id="client_name" value="<%=client_name %>">
<table width="100%"  align="center" border="1"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>客户编号</td>
		<td>客户名称</td>
		<td>客户类型</td>
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
		
		String client_id = StringUtils.nullToStr(map.get("id"));
		
		double curYs = 0l;
		double curYf = 0l;
		
		if(qcMap != null && wlMap != null){
			double qcys = qcMap.get(client_id+"应收")==null?0:((Double)qcMap.get(client_id+"应收")).doubleValue();  //昨日期初应收数		
			double bqfs = wlMap.get(client_id+"应收发生")==null?0:((Double)wlMap.get(client_id+"应收发生")).doubleValue();  //昨日应收发生
			double ysje = wlMap.get(client_id+"已收发生")==null?0:((Double)wlMap.get(client_id+"已收发生")).doubleValue();  //昨日已收发生
			
			curYs = qcys + bqfs - ysje;  //当前应收数
			
			
			double qcyf = qcMap.get(client_id+"应付")==null?0:((Double)qcMap.get(client_id+"应付")).doubleValue();  //昨日期初应付数		
			double bqfsyf = wlMap.get(client_id+"应付发生")==null?0:((Double)wlMap.get(client_id+"应付发生")).doubleValue();  //本期发生
			double yfje = wlMap.get(client_id+"已付发生")==null?0:((Double)wlMap.get(client_id+"已付发生")).doubleValue();  //本期已收
			
			curYf = qcyf + bqfsyf - yfje;  //当前应付数
		}
	%>
	<tr>
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("client_type")) %></td>
		<td><%=JMath.round(xe,2) %></td>
		<td><%=JMath.round(curYs,2) %></td>
		<td><%=JMath.round(curYf,2) %></td>
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
