<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Hjd hjd = (Hjd)VS.findValue("hjd");
List hjdProducts = (List)VS.findValue("hjdProducts");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>换件单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">换件单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(hjd.getId()) %></td>	
		<td class="a1">换件日期</td>
		<td class="a2"><%=StringUtils.nullToStr(hjd.getHj_date()) %></td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(hjd.getJsr())) %></td>	    
		
		<td class="a1" width="15%">换件单状态</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(hjd.getState()) %></td>			
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">商品详细信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="hjdTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="25%">商品名称</td>
		<td width="20%">规格</td>
		<td width="15%">旧序列号</td>
		<td width="15%">新序列号</td>
		<td width="25%">备注</td>
	</tr>
	</thead>
<%
if(hjdProducts!=null && hjdProducts.size()>0){
	for(int i=0;i<hjdProducts.size();i++){
		HjdProduct hjdProduct = (HjdProduct)hjdProducts.get(i);
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(hjdProduct.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(hjdProduct.getProduct_xh()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(hjdProduct.getOqz_serial_num()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(hjdProduct.getNqz_serial_num()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(hjdProduct.getProduct_remark()) %></td>
	</tr>
<%
	}
}
%>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">备注</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="hjd.remark" id="remark" style="width:75%" maxlength="500" readonly><%=StringUtils.nullToStr(hjd.getRemark()) %></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
