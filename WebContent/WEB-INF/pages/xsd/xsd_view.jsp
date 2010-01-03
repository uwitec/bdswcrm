<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List userList = (List)VS.findValue("userList");

List xsdProducts = (List)VS.findValue("xsdProducts");

Xsd xsd = (Xsd)VS.findValue("xsd");

%>

<html>
<head>
<title>销售订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function chgKpTyle(vD){
		var obj_mc1 = document.getElementById("mc1");
		var obj_mc2 = document.getElementById("mc2");
		
		var obj_dz1 = document.getElementById("dz1");
		var obj_dz2 = document.getElementById("dz2");
		
		var obj_dh1 = document.getElementById("dh1");
		var obj_dh2 = document.getElementById("dh2");
		
		var obj_zh1 = document.getElementById("zh1");
		var obj_zh2 = document.getElementById("zh2");
		
		var obj_sh1 = document.getElementById("sh1");
		var obj_sh2 = document.getElementById("sh2");
		
		if(vD == "出库单"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "none";
			obj_dz2.style.display = "none";
			
			obj_dh1.style.display = "none";
			obj_dh2.style.display = "none";
			
			obj_zh1.style.display = "none";
			obj_zh2.style.display = "none";
			
			obj_sh1.style.display = "none";
			obj_sh2.style.display = "none";			
		}else if(vD == "普通发票"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "";
			obj_dz2.style.display = "";
			
			obj_dh1.style.display = "";
			obj_dh2.style.display = "";
			
			obj_zh1.style.display = "none";
			obj_zh2.style.display = "none";
			
			obj_sh1.style.display = "none";
			obj_sh2.style.display = "none";			
		}else if(vD == "增值发票"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "";
			obj_dz2.style.display = "";
			
			obj_dh1.style.display = "";
			obj_dh2.style.display = "";
			
			obj_zh1.style.display = "";
			obj_zh2.style.display = "";
			
			obj_sh1.style.display = "";
			obj_sh2.style.display = "";			
		}		
	}	
</script>
</head>
<body onload="chgKpTyle('<%=StringUtils.nullToStr(xsd.getFplx()) %>');">
<form name="xsdForm" action="saveXsd.html" method="post">
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
		<td class="a1" width="15%">客户付款类型</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getSklx()) %></td>		
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(xsd.getFzr())) %></td>	
	</tr>
	<tr>	
		<td class="a1">合计金额</td>
		<td class="a2"><%=JMath.round(xsd.getXsdje()) %></td>
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
		<td class="a2" width="35%"><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(xsd.getStore_id())) %></td>
		<td class="a1">出库经手人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(xsd.getCk_jsr())) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">物流状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(xsd.getState()) %></td>
		<td class="a1">出库日期</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getCk_date()) %></td>		
	</tr>
	<tr>	
		<td class="a1" width="15%">运输方式</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(xsd.getYsfs()) %></td>
		<td class="a1" width="15%">货单号</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getJob_no()) %></td>										
	</tr>
	<tr>
		<td class="a1" width="15%">查询电话</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getCx_tel()) %></td>						
		<td class="a1" width="15%">发货时间</td>
		<td class="a2"><%=StringUtils.nullToStr(xsd.getSend_time()) %></td>										
	</tr>	
</table>
<BR>
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
		<td>工分</td>
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
		<td class="a2"><%=JMath.round(xsdProduct.getGf(),2) %></td>
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
		<td colspan="4">开票信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">发票类型</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(xsd.getFplx()) %></td>
		<td class="a1" width="15%" id="mc1">名称</td>
		<td class="a2" id="mc2"><%=StringUtils.nullToStr(xsd.getKp_mc()) %></td>				
	</tr>									
	<tr>
		<td class="a1" width="15%" id="dz1" style="display:none">地址</td>
		<td class="a2" id="dz2" style="display:none"><%=StringUtils.nullToStr(xsd.getKp_address()) %></td>	
		<td class="a1" width="15%" id="dh1" style="display:none">电话</td>
		<td class="a2" id="dh2" style="display:none"><%=StringUtils.nullToStr(xsd.getKp_dh()) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%" id="zh1" style="display:none">开户行账号</td>
		<td class="a2"  id="zh2" style="display:none"><%=StringUtils.nullToStr(xsd.getKhhzh()) %></td>	
		<td class="a1" width="15%" id="sh1" style="display:none">税号</td>
		<td class="a2" id="sh2" style="display:none"><%=StringUtils.nullToStr(xsd.getSh()) %></td>		
	</tr>	
	
	<tr>
		<td class="a1" width="15%">发票信息摘要</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(xsd.getFpxx()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3"><%=StringUtils.nullToStr(xsd.getMs()) %></td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();;">
		</td>
	</tr>
</table>
</body>
</html>
