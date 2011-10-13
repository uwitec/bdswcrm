<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List productList = (List)VS.findValue("productList");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存情况</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script>
function pageRefresh(){
	document.refreshFormMain.submit();
}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<form name="refreshFormMain" action="getGysKcList.html" method="post">
</form>
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>库 存 情 况</B></font></TD>
		    <TD align="center">
		    	<input type="button" name="btnRes" value="刷新" onclick="pageRefresh();">
		    </TD>			    
		</TR>
		
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>序号</TD>
			<TD class=ReportHead>商品编码</TD>
			<TD class=ReportHead>商品名称</TD>
			<TD class=ReportHead>规格</TD>
			<TD class=ReportHead>库存数量</TD>
			<TD class=ReportHead>零售报价</TD>
			<TD class=ReportHead>零售限价</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
if(productList != null && productList.size()>0){
	for(int i=0;i<productList.size();i++){
		Map map = (Map)productList.get(i);
		
		double lsbj = map.get("lsbj")==null?0:((Double)map.get("lsbj")).doubleValue();
		double lsxj = map.get("lsxj")==null?0:((Double)map.get("lsxj")).doubleValue();
		
		String num = StringUtils.nullToStr(map.get("kc_nums"));
		if(num.equals("")){
			num = "0";
		}
%>
		<TR>
			<TD class=ReportItemXh><%=i+1 %>&nbsp;</TD>
			<TD class=ReportItemXh><%=StringUtils.nullToStr(map.get("product_id")) %></TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_name")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_xh")) %>&nbsp;</TD>
			<TD class=ReportItemXh><%=num %>&nbsp;</TD>
			<td class=ReportItemMoney><%=JMath.round(lsbj,2) %>&nbsp;</td>
			<td class=ReportItemMoney><%=JMath.round(lsxj,2) %>&nbsp;</td>
		</TR>
<%
	}
}
%>
</TABLE>
<table width="99%">
		<tr>
			<td colspan="2" align="right" height="30" width="100%">生成报表时间：<%=DateComFunc.getCurTime() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> 
</center>
</div>
</body>
</html>