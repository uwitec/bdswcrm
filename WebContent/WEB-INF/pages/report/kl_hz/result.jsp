<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List productList = (List)VS.findValue("productList");

String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
String store_id = StringUtils.nullToStr(request.getParameter("store_id"));
String kl_day = StringUtils.nullToStr(request.getParameter("kl_day"));
 

String conStr = "";
if(!product_kind.equals("")){
	String[] arryItems = product_kind.split(",");
	String kind_name = "";
	if(arryItems != null && arryItems.length >0){
		for(int i=0;i<arryItems.length;i++){
			if(kind_name.equals("")){
				kind_name = StaticParamDo.getProductKindNameById(arryItems[i]);
			}else{
				kind_name += "，" + StaticParamDo.getProductKindNameById(arryItems[i]);
			}
			
		}
	}
	conStr += "&nbsp;&nbsp;<b>商品类别：</b>" + kind_name;
}
if(!store_id.equals("")){
	conStr += "&nbsp;&nbsp;<b>库房：</b>" + StaticParamDo.getStoreNameById(store_id);
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库龄汇总——统计结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>库龄汇总</B></font><br><%=conStr %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>序号</TD>
			<TD class=ReportHead>序列号</TD>
			<TD class=ReportHead>商品编码</TD>
			<TD class=ReportHead>商品名称</TD>
			<TD class=ReportHead>规格</TD>
			<TD class=ReportHead>库龄</TD>
			<TD class=ReportHead>考核成本</TD>
			<TD class=ReportHead>零售报价</TD>
			<TD class=ReportHead>零售限价</TD>
			<TD class=ReportHead>分销价</TD>
			<TD class=ReportHead>分销限价</TD>
			<TD class=ReportHead>工分</TD>
			<TD class=ReportHead>点杀</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
if(productList != null && productList.size()>0){
	for(int i=0;i<productList.size();i++){
		Map map = (Map)productList.get(i);
		
		double khcbj = map.get("khcbj")==null?0:((Double)map.get("khcbj")).doubleValue();
		double lsbj = map.get("lsbj")==null?0:((Double)map.get("lsbj")).doubleValue();
		double lsxj = map.get("lsxj")==null?0:((Double)map.get("lsxj")).doubleValue();
		double fxxj = map.get("fxxj")==null?0:((Double)map.get("fxxj")).doubleValue();
		double fxbj = map.get("fxbj")==null?0:((Double)map.get("fxbj")).doubleValue();
		double dss = map.get("dss")==null?0:((Double)map.get("dss")).doubleValue();		
		double gf = map.get("gf")==null?0:((Double)map.get("gf")).doubleValue();
		
		 
%>
		<TR>
			<TD class=ReportItemXh nowrap="nowrap"><%=i+1 %>&nbsp;</TD>
			<TD class=ReportItemXh nowrap="nowrap"><%=StringUtils.nullToStr(map.get("serial_num"))%>&nbsp;</TD>
			<TD class=ReportItemXh nowrap="nowrap"><%=StringUtils.nullToStr(map.get("product_id")) %></TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_name")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_xh")) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap"><%=StringUtils.nullToStr(map.get("klday")) %>&nbsp;</TD>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(khcbj,2) %>&nbsp;</td>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(lsbj,2) %>&nbsp;</td>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(lsxj,2) %>&nbsp;</td>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(fxbj,2) %>&nbsp;</td>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(fxxj,2) %>&nbsp;</td>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(gf) %>&nbsp;</td>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(dss) %>&nbsp;</td>
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
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
	<!--  <input type="button" name="button_print" value=" 导 出 " onclick="document.reportForm.submit();">--> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showKlCondition.html';"> 
</center>
<form name="reportForm" action="DownLoadXlsServlet" method="post">
<input type="hidden" name="action" value="exportProductKcNumsResult">
<input type="hidden" name="product_kind" value="<%=product_kind %>">
<input type="hidden" name="product_name" value="<%=product_name %>">
<input type="hidden" name="store_id" value="<%=store_id %>">
 
</form>
</div>
</body>
</html>