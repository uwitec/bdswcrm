<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
List storeList = (List)VS.findValue("storeList");

Kfdb kfdb = (Kfdb)VS.findValue("kfdb");
List kfdbProducts = (List)VS.findValue("kfdbProducts");

String msg = StringUtils.nullToStr(VS.findValue("msg"));

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库房调拨</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">库房调拨</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2"><%=StringUtils.nullToStr(kfdb.getId()) %></td>	
		<%
		String date = StringUtils.nullToStr(kfdb.getCk_date());
		if(date.equals("")){
			date = DateComFunc.getToday();
		}
		%>
		<td class="a1">日期</td>
		<td class="a2"><%=StringUtils.nullToStr(kfdb.getCk_date()) %></td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">调出仓库</td>
		<td class="a2"><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(kfdb.getCk_store_id())) %></td>
		<td class="a1" width="15%">调入仓库</td>
		<td class="a2"><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(kfdb.getRk_store_id())) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">调拨申请单编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(kfdb.getDbsq_id()) %></td>
		<td class="a1" width="15%">申请人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(kfdb.getSqr())) %></td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(kfdb.getJsr())) %></td>
		<td class="a1">状态</td>
		<td class="a2"><%=StringUtils.nullToStr(kfdb.getState()) %></td>			
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
<table width="100%"  align="center" id="kfdbTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>商品名称</td>
		<td>规格</td>
		<td>数量</td>
	</tr>
	</thead>
<%
if(kfdbProducts!=null && kfdbProducts.size()>0){
	for(int i=0;i<kfdbProducts.size();i++){
		Map kfdbProduct = (Map)kfdbProducts.get(i);
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(kfdbProduct.get("product_name")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(kfdbProduct.get("product_xh")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(kfdbProduct.get("nums")) %></td>
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
			<textarea rows="3" name="kfdb.remark" id="remark" style="width:75%" maxlength="500" readonly><%=StringUtils.nullToStr(kfdb.getRemark()) %></textarea>
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
