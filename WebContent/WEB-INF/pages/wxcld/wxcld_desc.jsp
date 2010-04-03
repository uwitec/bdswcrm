<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Map results = (Map)VS.findValue("wxcldProducts");
if(null==results)
{
  results=new HashMap();
}
%>

<html>
<head>
<title></title>
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
		<td colspan="2">维修商品详细明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="20%">商品名称</td>
		<td width="10%">商品规格</td>		 		 
		<td width="20%">序列号</td>
		<td width="10%">处理方式</td>		 		 
		<td width="20%">故障类型</td>			 
		<td width="10%">备注</td>
	</tr>
	</thead>
	 
	  
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><%=StringUtils.nullToStr(results.get("product_name")) %></td>
		<td><%=StringUtils.nullToStr(results.get("product_xh")) %></td>
		<td><%=StringUtils.nullToStr(results.get("product_serial_num")) %></td>		  
		<td><%=StringUtils.nullToStr(results.get("product_clfs")) %></td>
		<td><%=StringUtils.nullToStr(results.get("product_wxlx")) %></td>
		<td><%=StringUtils.nullToStr(results.get("product_remark")) %></td>		 
	</tr>
	 
</table>
</body>
</html>
