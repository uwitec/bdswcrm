<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("kcpdDesc");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存盘点明细</title>
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
		<td colspan="2">库存盘点明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="25%">商品名称</td>
		<td width="25%">规格</td>
		<td width="10%">账面数量</td>
		<td width="10%">实际数量</td>
		<td width="10%">盈亏</td>
		<td width="20%">强制序列号</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		Map kcpdDesc = (Map)it.next();
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><%=StringUtils.nullToStr(kcpdDesc.get("Product_name")) %></td>
		<td><%=StringUtils.nullToStr(kcpdDesc.get("Product_xh")) %></td>
		<td><%=StringUtils.nullToStr(kcpdDesc.get("Kc_nums")) %></td>
		<td><%=StringUtils.nullToStr(kcpdDesc.get("Sj_nums")) %></td>
		<td><%=StringUtils.nullToStr(kcpdDesc.get("Yk")) %></td>
		<td><%=StringUtils.nullToStr(kcpdDesc.get("Qz_serial_num")) %></td>
	</tr>
	
	<%
	}
	%>
</table>
</body>
</html>
