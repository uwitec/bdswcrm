<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List fxddProducts = (List)VS.findValue("fxddProducts");
Fxdd fxdd = (Fxdd)VS.findValue("fxdd");
Clients clients = (Clients)VS.findValue("clients");

String fxdd_id = "";
String creatDate = "";  //创建时间
String ysfs = "";       //运输方式
String state = "";      //状态
String hjje = "";       //合计金额
String remark = "";     //备注

String client_name = StringUtils.nullToStr(clients.getName());  //客户名称
String address = StringUtils.nullToStr(clients.getAddress());
String lxr = StringUtils.nullToStr(clients.getLxr());
String lxdh = StringUtils.nullToStr(clients.getLxdh());


if(fxdd != null){
	fxdd_id = StringUtils.nullToStr(fxdd.getFxdd_id());
	creatDate = StringUtils.nullToStr(fxdd.getCreatdate());
	if(creatDate.equals("")){
		creatDate = DateComFunc.getToday();
	}
	
	ysfs = StringUtils.nullToStr(fxdd.getYsfs());	
	state = StringUtils.nullToStr(fxdd.getState());
	hjje = JMath.round(fxdd.getHjje());
	remark = StringUtils.nullToStr(fxdd.getRemark());
	
	address = StringUtils.nullToStr(fxdd.getAddress());
	lxr = StringUtils.nullToStr(fxdd.getLxr());
	lxdh = StringUtils.nullToStr(fxdd.getLxdh());
}

%>

<html>
<head>
<title>采购订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<form name="fxddForm" action="updateFxdd.html" method="post">
<input type="hidden" name="fxdd.fxdd_id" value="<%=fxdd_id %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">订单基本信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">创建时间</td>
		<td class="a2" width="35%"><%=creatDate %></td>
		<td class="a1" width="15%">单位名称</td>
		<td class="a2"><%=client_name %></td>		
	</tr>
	<tr>	
		<td class="a1">地址</td>
		<td class="a2"><%=address %></td>	
		<td class="a1">联系人</td>
		<td class="a2"><%=lxr %></td>		
	</tr>
	<tr>	
		<td class="a1">联系电话</td>
		<td class="a2"><%=lxdh %></td>
		<td class="a1" width="15%">订单状态</td>
		<td class="a2"><%=state %></td>				
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
		<td class="a1" width="15%">物流状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(fxdd.getWlzt()) %></td>
		<td class="a1" width="15%">运输方式</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(fxdd.getYsfs()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">货单号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(fxdd.getJob_no()) %></td>								
		<td class="a1" width="15%">查询电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(fxdd.getCx_tel()) %></td>						
	</tr>
	<tr>
		<td class="a1" width="15%">发货时间</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(fxdd.getSend_time()) %></td>								
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
<table width="100%"  align="center" id="xsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>商品名称</td>
		<td>规格</td>
		<td>销售价格</td>
		<td>数量</td>
		<td>小计</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(fxddProducts!=null && fxddProducts.size()>0){
	for(int i=0;i<fxddProducts.size();i++){
		XsdProduct xsdProduct = (XsdProduct)fxddProducts.get(i);
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(xsdProduct.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(xsdProduct.getProduct_xh()) %></td>
		<td class="a2"><%=JMath.round(xsdProduct.getPrice()) %></td>
		<td class="a2"><%=xsdProduct.getNums() %></td>
		<td class="a2"><%=JMath.round(xsdProduct.getXj()) %></td>		
		<td class="a2"><%=StringUtils.nullToStr(xsdProduct.getRemark()) %></td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">	
		<td class="a1">订单合计金额</td>
		<td class="a2"><%=hjje %>元</td>
	</tr>
	<tr>
		<td class="a1" width="15%">备&nbsp;&nbsp;注</td>
		<td class="a2" width="85%">
			<textarea rows="2" name="fxdd.remark" id="remark" style="width:75%" readonly><%=remark %></textarea>
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();;">
		</td>
	</tr>
</table>
</form>
</body>
</html>
