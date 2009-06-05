<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String strTree = (String)VS.findValue("strTree");
Page results = (Page)VS.findValue("productPage");

String openerId = ParameterUtility.getStringParameter(request, "openerId","");
String product_kind_id = ParameterUtility.getStringParameter(request, "product_kind_id","");
%>

<html>
<head>
<title>选择产品</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<link href="tree/menu.css" rel="stylesheet" type="text/css">
<script src="tree/menu2.js" type="text/javascript"></script>

<script language="JavaScript">

function window_onload(){
	initialize();
}

function sel(product_id,product_xh,product_name,price,prop){
	var id = window.opener.document.getElementById("product_id");
	var xh = window.opener.document.getElementById("product_xh");
	var name = window.opener.document.getElementById("product_name");
	var pc = window.opener.document.getElementById("price");
	var pp = window.opener.document.getElementById("prop");
	
	id.value = product_id;
	xh.value = product_xh;
	name.value = product_name;
	pc.value = price;
	pp.value = prop;
	
	window.close();	
}
</script> 

</head>

<body oncontextmenu="return false;" onLoad="window_onload();" align="center">

<table width="100%" border="0" align="center" class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择产品</b></td>
	</tr>
</table>

<table width="100%" border="0" align="center" class="chart_info" cellpadding="0" cellspacing="0" height="450">
<tr>
	<td width="20%" class="a2"  valign="top">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td width="100%" valign="top">
						<script language="javascript" type="text/javascript">
							<%=strTree %>
							document.write(menu(0));
						</script></td>
			</tr>
		</table>
	</td>
	
	<td width="80%" class="a2"  valign="top">
<form name="myform" action="product_list.html" method="post">
<input type="hidden" name="product_kind_id" id="product_kind_id" value="<%=product_kind_id %>">
<input type="hidden" name="openerId" id="openerId" value="<%=openerId %>">	

		<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0">
			<thead>
			<tr>
				<td>产品型号</td>
				<td>产品名称</td>
				<td>所属产品组</td>
				<td>供应商名称</td>
				<td>成本价</td>
				<td>产品属性</td>
			</tr>
			</thead>
			<%
			List list = results.getResults();
			Iterator it = list.iterator();
			
			while(it.hasNext()){
				Map map = (Map)it.next();
			%>
			<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="左键点击选择" onclick="sel('<%=StringUtils.nullToStr(map.get("product_id")) %>','<%=StringUtils.nullToStr(map.get("product_xh")) %>','<%=StringUtils.nullToStr(map.get("product_name")) %>','<%=StringUtils.nullToStr(map.get("price")) %>','<%=StringUtils.nullToStr(map.get("prop")) %>');">
				<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
				<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
				<td><%=StringUtils.nullToStr(map.get("product_kind")) %></td>
				<td><%=StringUtils.nullToStr(map.get("gysmc")) %></td>
				<td><%=map.get("price") %></td>
				<td><%=map.get("prop") %></td>
			</tr>
			
			<%
			}
			%>
			<tr>
				<td class="page" colspan="8"><%=results.getPageScript() %></td>
			</tr>			
		</table>
		
</form>		
	</td>
</tr>
</table>

</body>
</html>
