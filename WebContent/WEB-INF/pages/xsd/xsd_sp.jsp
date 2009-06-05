<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List xsdProducts = (List)VS.findValue("xsdProducts");

Xsd xsd = (Xsd)VS.findValue("xsd");

String sp_type = StringUtils.nullToStr(xsd.getSp_type());
if(sp_type.equals("1")){
	sp_type = "客户存在超期应付款";
}else if(sp_type.equals("2")){
	sp_type = "超出客户限额并且订单商品价格低于最低限价";	
}else if(sp_type.equals("3")){
	sp_type = "超出客户限额";	
}else if(sp_type.equals("4")){
	sp_type = "订单商品价格低于最低限价";
}
%>

<html>
<head>
<title>审批销售订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	//提交审批
	function doSp(vl){
		document.xsdForm.sp_state.value = vl;
		document.xsdForm.submit();
	}
</script>
</head>
<body >
<form name="xsdForm" action="doSpXsd.html" method="post">
	<input type="hidden" name="id" value="<%=StringUtils.nullToStr(xsd.getId()) %>">
	<input type="hidden" name="sp_state" value="">
</form>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">销售订单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">销售订单编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(xsd.getId()) %></td>
		<td class="a1">创建时间</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getCreatdate()) %></td>
	</tr>
	<tr>	
		<td class="a1" width="15%">客户名称</td>
		<td class="a2"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(xsd.getClient_name())) %></td>		
		<td class="a1" width="15%">地址</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getKh_address()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">客户联系人</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getKh_lxr()) %></td>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getKh_lxdh()) %></td>			
	</tr>
	<tr>
		<td class="a1" width="15%">运输方式</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getYsfs()) %></td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(xsd.getFzr())) %></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">客户付款类型</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getSklx()) %></td>				
		<td class="a1" width="15%">销售单状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(xsd.getState()) %></td>	
	</tr>
	<tr height="35">
		<td class="a1" width="15%">出货仓库</td>
		<td class="a2" width="35%"><%=StaticParamDo.getStoreNameById(xsd.getStore_id()) %></td>		
		<td class="a1">合计金额</td>
		<td class="a2"><%=JMath.round(xsd.getXsdje()) %></td>
	</tr>
	<tr height="35">
		<td class="a1" width="15%">实际成交金额</td>
		<td class="a2" width="35%"><%=JMath.round(xsd.getSjcjje()) %></td>		
		<td class="a1" widht="20%">回款类型</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(xsd.getSkxs()) %></td>
	</tr>	
	<tr>
		<td class="a1" widht="20%">收款账户</td>
		<td class="a2"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(xsd.getSkzh())) %></td>
		<td class="a1">收款金额</td>
		<td class="a2"><%=JMath.round(xsd.getSkje()) %></td>
	</tr>	
	<tr>
		<td class="a1" widht="20%">操作人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(xsd.getCzr())) %></td>
		<td class="a1">成交时间</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getCz_date()) %></td>
	</tr>
	<tr>
		<td class="a1" widht="20%">审批类型</td>
		<td class="a2"><%=sp_type %></td>
	</tr>		
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">产品详细信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="xsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>销售价格</td>
		<td>价格调整</td>
		<td>订单数量</td>
		<td>订单金额</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(xsdProducts!=null && xsdProducts.size()>0){
	for(int i=0;i<xsdProducts.size();i++){
		XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(xsdProduct.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(xsdProduct.getProduct_xh()) %></td>
		<td class="a2"><%=JMath.round(xsdProduct.getPrice(),2) %></td>
		<td class="a2"><%=JMath.round(xsdProduct.getJgtz(),2) %></td>
		<td class="a2"><%=xsdProduct.getNums() %></td>
		<td class="a2"><%=JMath.round(xsdProduct.getXj(),2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(xsdProduct.getRemark()) %></td>
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
		<td colspan="2">描述信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">描述信息</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="xsd.ms" id="ms" style="width:75%" readonly><%=StringUtils.nullToStr(xsd.getMs()) %></textarea>
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="reset" name="button2" value="审批通过" class="css_button3" onclick="doSp('3');">&nbsp;&nbsp;
			<input type="reset" name="button2" value="审批不通过" class="css_button3" onclick="doSp('4');">&nbsp;&nbsp;		
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();;">
		</td>
	</tr>
</table>
</body>
</html>
