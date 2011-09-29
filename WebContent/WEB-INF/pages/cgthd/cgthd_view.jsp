<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Cgthd cgthd = (Cgthd)VS.findValue("cgthd");
List cgthdProducts = (List)VS.findValue("cgthdProducts");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>采购退货单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购退货单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">退货单编号</td>
		<td class="a2"><%=StringUtils.nullToStr(cgthd.getId()) %></td>	
		<td class="a1">退货日期</td>
		<td class="a2"><%=StringUtils.nullToStr(cgthd.getTh_date()) %></td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">供货单位</td>
		<td class="a2"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(cgthd.getProvider_name())) %></td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(cgthd.getJsr())) %></td>
	</tr>
	<tr>
		<td class="a1">退款方式</td>
		<td class="a2"><%=StringUtils.nullToStr(cgthd.getTkfs()) %></td>	
		<td class="a1">状态</td>
		<td class="a2"><%=StringUtils.nullToStr(cgthd.getState()) %></td>			
	</tr>
	<tr>
		<td class="a1" widht="20%">账户名称</td>
		<td class="a2"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(cgthd.getZhmc())) %></td>
		<td class="a1">合计金额</td>
		<td class="a2"><%=JMath.round(cgthd.getTkzje(),2) %></td>
	</tr>
	<tr>
		<td class="a1">出货库房</td>
		<td class="a2"><%=StaticParamDo.getStoreNameById(cgthd.getStore_id()) %></td>
		<td class="a1">成交时间</td>
		<td class="a2"><%=cgthd.getCz_date() %></td>		
	</tr>
	<tr>
		<td class="a1">操作人</td>
		<td class="a2" colspan="3"><%=StaticParamDo.getRealNameById(cgthd.getCzr()) %></td>
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
<table width="100%"  align="center" id="thtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>商品名称</td>
		<td>规格</td>
		<td>退货价格</td>
		<td>数量</td>
		<td>小计</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(cgthdProducts != null && cgthdProducts.size()>0){
	for(int i=0;i<cgthdProducts.size();i++){
		CgthdProduct cgthdProduct = (CgthdProduct)cgthdProducts.get(i);
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(cgthdProduct.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(cgthdProduct.getProduct_xh()) %></td>
		<td class="a2"><%=JMath.round(cgthdProduct.getTh_price(),2) %></td>
		<td class="a2"><%=cgthdProduct.getNums() %></td>
		<td class="a2"><%=JMath.round(cgthdProduct.getXj(),2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(cgthdProduct.getRemark()) %></td>
	</tr>
<%		
	}
}
%>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="cgthd.remark" id="remark" style="width:75%" maxlength="500" readonly><%=StringUtils.nullToStr(cgthd.getRemark()) %></textarea>
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
