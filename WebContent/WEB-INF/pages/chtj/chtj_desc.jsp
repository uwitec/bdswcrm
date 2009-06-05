<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("chtjDesc");

%>

<html>
<head>
<title>存货调价明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}		
</script>
</head>
<body oncontextmenu="return false;">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">存货调价明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>原价格</td>
		<td>调整后价格</td>
		<td>备注</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		ChtjDesc chtjDesc = (ChtjDesc)it.next();
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><%=StringUtils.nullToStr(chtjDesc.getProduct_name()) %></td>
		<td><%=StringUtils.nullToStr(chtjDesc.getProduct_xh()) %></td>
		<td><%=JMath.round(chtjDesc.getYsjg()) %></td>
		<td><%=JMath.round(chtjDesc.getTzjg()) %></td>
		<td><%=StringUtils.nullToStr(chtjDesc.getRemark()) %></td>
	</tr>
	
	<%
	}
	%>
</table>
</body>
</html>
