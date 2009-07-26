<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Ckd ckd = (Ckd)VS.findValue("ckd");
List ckdProducts = (List)VS.findValue("ckdProducts");

%>

<html>
<head>
<title>出库单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">出库单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">出库单编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(ckd.getCkd_id()) %></td>
		<td class="a1">创建时间</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(ckd.getCreatdate()) %></td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">客户名称</td>
		<td class="a2"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(ckd.getClient_name())) %></td>	
		<td class="a1">联系人</td>
		<td class="a2"><%=StringUtils.nullToStr(ckd.getClient_lxr()) %></td>
	</tr>
	<tr>		
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><%=StringUtils.nullToStr(ckd.getClient_lxr_tel()) %></td>	
		<td class="a1">地址</td>
		<td class="a2"><%=StringUtils.nullToStr(ckd.getClient_lxr_address()) %></td>
	</tr>	
	<tr>
		<td class="a1" width="15%">销售单编号</td>
		<td class="a2"><%=StringUtils.nullToStr(ckd.getXsd_id()) %></td>
		<td class="a1" width="15%">销售负责人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(ckd.getXsry())) %></td>				
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">物流信息</td>
	</tr>
	</thead>
	<tr>		
		<td class="a1" width="15%">出货库房</td>
		<td class="a2" width="35%"><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(ckd.getStore_id())) %></td>	
		<td class="a1" width="15%">出库经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(ckd.getFzr())) %></td>	
	</tr>	
	<tr>
		<td class="a1" width="15%">物流状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(ckd.getState()) %></td>
		<td class="a1">出库时间</td>
		<td class="a2"><%=StringUtils.nullToStr(ckd.getCk_date()) %></td>		
	</tr>
	<tr>		
		<td class="a1" width="15%">运输方式</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(ckd.getYsfs()) %></td>
		<td class="a1" width="15%">货单号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(ckd.getJob_no()) %></td>		
	</tr>
	<tr>							
		<td class="a1" width="15%">查询电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(ckd.getCx_tel()) %></td>	
		<td class="a1" width="15%">发货时间</td>
		<td class="a2"><%=StringUtils.nullToStr(ckd.getSend_time()) %></td>									
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
<table width="100%"  align="center" id="cktable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>数量</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(ckdProducts!=null && ckdProducts.size()>0){
	for(int i=0;i<ckdProducts.size();i++){
		Map ckdProduct = (Map)ckdProducts.get(i);
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(ckdProduct.get("product_name")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(ckdProduct.get("product_xh")) %></td>	
		<td class="a2"><%=StringUtils.nullToStr(ckdProduct.get("nums")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(ckdProduct.get("remark")) %></td>
	</tr>
<%
	}
}
%>	
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">描述信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">描述信息</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="ckd.ms" id="ms" style="width:75%" maxlength="500" readonly><%=StringUtils.nullToStr(ckd.getMs()) %></textarea>
		</td>
	</tr>	
</table>
<br>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
