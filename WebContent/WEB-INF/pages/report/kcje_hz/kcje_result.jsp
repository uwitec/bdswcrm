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
	conStr += "&nbsp;&nbsp;<b>商品类别：</b>" + kind_name;
}
if(!store_id.equals("")){
	conStr += "&nbsp;&nbsp;<b>库房：</b>" + StaticParamDo.getStoreNameById(store_id);
}
%>

<html>
<head>
<title>库存金额汇总——统计结果</title>
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
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>库存金额汇总</B></font><br><%=conStr %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>序号</TD>
			<TD class=ReportHead>商品编码</TD>
			<TD class=ReportHead>商品名称</TD>
			<TD class=ReportHead>规格</TD>
			<TD class=ReportHead>库存数量</TD>
			<TD class=ReportHead>单位成本</TD>
			<TD class=ReportHead>库存成本</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
int hj_kcNums = 0;
double hj_kccb = 0;
if(productList != null && productList.size()>0){
	for(int i=0;i<productList.size();i++){
		Map map = (Map)productList.get(i);
		
		double price = map.get("price")==null?0:((Double)map.get("price")).doubleValue();
		String num = StringUtils.nullToStr(map.get("kc_nums"));
		if(num.equals("")){
			num = "0";
		}
		int kcNums = Integer.parseInt(num);
		
		hj_kcNums += kcNums;
		hj_kccb += price*kcNums;
%>
		<TR>
			<TD class=ReportItemXh><%=i+1 %>&nbsp;</TD>
			<TD class=ReportItemXh><%=StringUtils.nullToStr(map.get("product_id")) %></TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_name")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_xh")) %>&nbsp;</TD>
			<TD class=ReportItem><%=num %>&nbsp;</TD>
			<td class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</td>
			<td class=ReportItemMoney><%=JMath.round(price*kcNums,2) %>&nbsp;</td>
		</TR>
<%
	}
}
%>
		<TR>
			<TD class=ReportItemXh><b>合计</b></TD>
			<TD class=ReportItemXh>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem><%=hj_kcNums %>&nbsp;</TD>
			<td class=ReportItemMoney>&nbsp;</td>
			<td class=ReportItemMoney><%=JMath.round(hj_kccb,2) %>&nbsp;</td>
		</TR>
</TABLE>
<table width="99%">
		<tr>
			<td colspan="2" align="right" height="30" width="100%">生成报表时间：<%=DateComFunc.getCurTime() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
	<input type="button" name="button_print" value=" 导 出 " onclick="document.reportForm.submit();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showKcJeCondition.html';"> 
</center>
<form name="reportForm" action="DownLoadXlsServlet" method="post">
<input type="hidden" name="action" value="exportProductKcjeResult">
<input type="hidden" name="product_kind" value="<%=product_kind %>">
<input type="hidden" name="product_name" value="<%=product_name %>">
<input type="hidden" name="store_id" value="<%=store_id %>">
<input type="hidden" name="flag" value="<%=flag %>">
<input type="hidden" name="state" value="<%=state %>">
</form>
</body>
</html>