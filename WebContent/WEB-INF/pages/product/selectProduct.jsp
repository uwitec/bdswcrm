<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("productPage");
String openerId = ParameterUtility.getStringParameter(request, "openerId","");

String product_name = StringUtils.nullToStr((String)VS.findValue("product_name"));
String product_kind = StringUtils.nullToStr((String)VS.findValue("product_kind"));
List kindList = (List)VS.findValue("kindList");
String prop = StringUtils.nullToStr((String)VS.findValue("prop"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择商品</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<link href="tree/menu.css" rel="stylesheet" type="text/css">

<script language="JavaScript">

function sel(product_id,product_xh,product_name,price,qz_serial_num){
	var id = window.opener.document.getElementById("product_id_<%=openerId%>");
	var xh = window.opener.document.getElementById("product_xh_<%=openerId%>");
	var name = window.opener.document.getElementById("product_name_<%=openerId%>");
	var pc = window.opener.document.getElementById("price_<%=openerId%>");
	
	var qz_flag = window.opener.document.getElementById("qz_flag_<%=openerId%>");
	
	if(id != null) id.value = product_id;
	if(xh != null) xh.value = product_xh;
	if(name != null) name.value = product_name;
	if(pc != null) pc.value = price;
	
	if(qz_flag != null) qz_flag.value = qz_serial_num;
	
	window.close();	
}

function clearAll(){
	document.myform.product_kind.value = "";
	document.myform.product_name.value = "";
	document.myform.prop.value = "";
}
</script> 

</head>

<body  align="center">
<form name="myform" action="selectProduct.html" method="post">
<input type="hidden" name="openerId" id="openerId" value="<%=openerId %>">	
<table width="100%" border="0" align="center" class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择商品</b></td>
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			商品：<input type="text" name="product_name" value="<%=product_name %>" size="20">&nbsp;&nbsp;
			类别：
			<select name="product_kind">
				<option value=""></option>
				<%
				if(kindList != null &&  kindList.size()>0){
					for(int i=0;i<kindList.size();i++){
						Map map = (Map)kindList.get(i);
						String id = StringUtils.nullToStr(map.get("id"));
						String name = StringUtils.nullToStr(map.get("name"));
						for(int k=0;k<id.length()-3;k++){
							name = "　" + name;
						}
				%>
				<option value="<%=id %>" <%if(product_kind.equals(id)) out.print("selected"); %>><%=name %></option>
				<%
					}
				}
				%>
			</select>&nbsp;&nbsp;
			属性：<select name="prop">
				<option value=""></option>
				<option value="库存商品" <%if(prop.equals("库存商品")) out.print("selected"); %>>库存商品</option>
				<option value="服务/劳务" <%if(prop.equals("服务/劳务")) out.print("selected"); %>>服务/劳务</option>
			</select>&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value="查询" class="css_button">
			<input type="button" name="buttonQk" value="清空" class="css_button" onclick="clearAll();">
		</td>				
	</tr>	
</table>

<table width="100%" border="0" align="center" class="chart_info" cellpadding="0" cellspacing="0" height="450">
<tr>
	<td width="80%" class="a2"  valign="top">
		<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0">
			<thead>
			<tr>
				<td>商品名称</td>
				<td>商品规格</td>
				<td>成本价</td>
				<td>商品属性</td>
			</tr>
			</thead>
			<%
			List list = results.getResults();
			Iterator it = list.iterator();
			
			while(it.hasNext()){
				Map map = (Map)it.next();
				
				double price = map.get("price")==null?0:((Double)map.get("price")).doubleValue();
			%>
			<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="左键点击选择" onclick="sel('<%=StringUtils.nullToStr(map.get("product_id")) %>','<%=StringUtils.nullToStr(map.get("product_xh")) %>','<%=StringUtils.nullToStr(map.get("product_name")) %>','<%=JMath.round(price) %>','<%=StringUtils.nullToStr(map.get("qz_serial_num")) %>');">
				<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
				<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
				<td><%=JMath.round(price,2) %></td>
				<td><%=map.get("prop") %></td>
			</tr>
			
			<%
			}
			%>
			<tr>
				<td class="page" colspan="8"><%=results.getPageScript() %></td>
			</tr>			
		</table>	
	</td>
</tr>
</table>
</form>	
</body>
</html>
