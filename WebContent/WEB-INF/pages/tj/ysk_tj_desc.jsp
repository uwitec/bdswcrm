<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("yskDesc");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>应收款统计明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewStore.html?id="+id;
		var fea ='width=600,height=400,left=150,top=15,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
		
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="listYskTj.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>应收款统计明细</b></td>			
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td >客户名称</td>
		<td >应收金额</td>
		<td >实收金额</td>
		<td >收款类型</td>
		<td >应收日期</td>
		<td >收款日期</td>
		<td >销售出库单编号</td>
		<td >销售人员</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		double ysje = map.get("ysje")==null?0:((Double)map.get("ysje")).doubleValue();
		double ssje = map.get("ssje")==null?0:((Double)map.get("ssje")).doubleValue();
	%>
	<tr>
		<td class="a1"><%=StringUtils.nullToStr(map.get("client_name")) %></td>
		<td class="a3"><%=JMath.round(ysje,2) %>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="a3"><%=JMath.round(ssje,2) %>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("sklx")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("ysrq")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("skrq")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("ckd_id")) %></td>
		<td class="a1"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("xsry"))) %></td>
	</tr>
	
	<%
	}
	%>
	<tr>
		<td class="a2" colspan="8"><input type="button" class="css_button2" value=" 返回 " onclick="history.go(-1);"></td>
	</tr>
</table>
</form>
</body>
</html>
