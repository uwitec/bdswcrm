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
String flag = StringUtils.nullToStr(request.getParameter("flag"));
String state = StringUtils.nullToStr(request.getParameter("state"));

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
	conStr += "&nbsp;&nbsp;<b>产品类别：</b>" + kind_name;
}
if(!store_id.equals("")){
	conStr += "&nbsp;&nbsp;<b>库房：</b>" + StaticParamDo.getStoreNameById(store_id);
}
%>

<html>
<head>
<title>零售库存数量汇总——统计结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>零售库存数量汇总</B></font><br><%=conStr %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead nowrap="nowrap">序号</TD>
			<!--  <TD class=ReportHead>商品编码</TD>-->
			<TD class=ReportHead nowrap="nowrap">商品名称</TD>
			<TD class=ReportHead nowrap="nowrap">规格</TD>
			<TD class=ReportHead nowrap="nowrap">库存数量</TD>
			<TD class=ReportHead nowrap="nowrap">零售报价</TD>
			<TD class=ReportHead nowrap="nowrap">零售限价</TD>
			<TD class=ReportHead nowrap="nowrap">考核成本</TD>
			<TD class=ReportHead nowrap="nowrap">比例点杀</TD>
			<TD class=ReportHead nowrap="nowrap">金额点杀</TD>
			<TD class=ReportHead nowrap="nowrap">产品卖点</TD>
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
		double dss = map.get("dss")==null?0:((Double)map.get("dss")).doubleValue();		
		double gf = map.get("gf")==null?0:((Double)map.get("gf")).doubleValue();
		
		String num = StringUtils.nullToStr(map.get("kc_nums"));
		String ms=StringUtils.nullToStr(map.get("ms"));
		if(!ms.equals("") && ms.length()>20){
		     ms=ms.substring(0,20)+"...";
		}
		if(num.equals("")){
			num = "0";
		}
%>
		<TR>
			<TD class=ReportItemXh nowrap="nowrap"><%=i+1 %>&nbsp;</TD>
			<!--<TD class=ReportItemXh><%=StringUtils.nullToStr(map.get("product_id")) %></TD>-->
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_name")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_xh")) %>&nbsp;</TD>
			<TD class=ReportItemXh><%=num %>&nbsp;</TD>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(lsbj,2) %>&nbsp;</td>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(lsxj,2) %>&nbsp;</td>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(khcbj,2) %>&nbsp;</td>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(gf) %>&nbsp;</td>
			<td class=ReportItemMoney nowrap="nowrap"><%=JMath.round(dss) %>&nbsp;</td>
			<td class=ReportItem title="<%=StringUtils.nullToStr(map.get("ms"))%>"><%=ms%>&nbsp;</td>
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
	<input type="button" name="button_print" value=" 导 出 " onclick="document.reportForm.submit();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showLsKcNumsCondition.html';"> 
</center>
<form name="reportForm" action="DownLoadXlsServlet" method="post">
<input type="hidden" name="action" value="exportProductLsKcNumsResult">
<input type="hidden" name="product_kind" value="<%=product_kind %>">
<input type="hidden" name="product_name" value="<%=product_name %>">
<input type="hidden" name="store_id" value="<%=store_id %>">
<input type="hidden" name="flag" value="<%=flag %>">
<input type="hidden" name="state" value="<%=state %>">
</form>
</body>
</html>