<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("bxfhdProducts");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>报修返还单明细</title>
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
		<td colspan="2">报修返还单明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="15%">商品名称</td>	
		<td width="15%">商品规格</td>
		<td width="5%">仓库</td>
		<td width="5%">单价</td>
		<td width="5%">数量</td>	
		<td width="5%">小计</td>
		<td width="12%">强制序列号</td>
		<td width="15%">商品附件</td>
		<td width="15%">备注</td>	
	</tr>
	</thead>
	 
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext())
	{
		BxfhdProduct bxfhdProduct = (BxfhdProduct)it.next();
	%>  
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td class="a2"><%=StringUtils.nullToStr(bxfhdProduct.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(bxfhdProduct.getProduct_xh()) %></td>	
		<td class="a2"><%="好件库" %>	</td>
		<td class="a2"><%=JMath.round(bxfhdProduct.getPrice()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(bxfhdProduct.getNums()) %></td>		 
		<td class="a2"><%=JMath.round(bxfhdProduct.getTotalmoney()) %></td>		
		<td class="a2"><%=StringUtils.nullToStr(bxfhdProduct.getQz_serial_num()) %></td>						
		<td class="a2"><%=StringUtils.nullToStr(bxfhdProduct.getCpfj()) %></td>	
		<td class="a2"><%=StringUtils.nullToStr(bxfhdProduct.getRemark()) %></td>	
	</tr>
	<%
	}
	%>
</table>
</body>
</html>
