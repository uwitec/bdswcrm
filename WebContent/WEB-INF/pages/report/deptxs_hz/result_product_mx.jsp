<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List resultList = (List)VS.findValue("resultList");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String xsry = StringUtils.nullToStr(request.getParameter("xsry"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String kind_name = StringUtils.nullToStr(request.getParameter("kind_name"));
String product_name = StringUtils.nullToStr(request.getParameter("product_name"));

String con = "";
con = "日期：" + start_date + "至" + end_date;
if(!client_name.equals("")){
	con += "&nbsp; 客户名称：" + StaticParamDo.getClientNameById(client_name);
}
if(!xsry.equals("")){
	con += "&nbsp; 销售人员：" + StaticParamDo.getRealNameById(xsry);
}
if(!kind_name.equals("")){
	con += "&nbsp;商品类别：" + kind_name;
}
if(!product_name.equals("")){
	con += "&nbsp;商品名称：" + product_name;
}
%>

<html>
<head>
<title>部门销售汇总--明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(product_id){
		document.myform.product_id.value = product_id;
		document.myform.submit();
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>部门销售汇总--明细</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>产品编码</TD>
			<TD class=ReportHead>产品名称</TD>
			<TD class=ReportHead>产品规格</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>金额</TD>		
		</TR>
	</THEAD>
	<TBODY>
<%
int hj_nums = 0;
double hj_je = 0;
if(resultList != null && resultList.size()>0){
	for(int i=0;i<resultList.size();i++){
		Map map = (Map)resultList.get(i);
		
		String product_id = StringUtils.nullToStr(map.get("product_id"));
		
		double je = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		String strNums = StringUtils.nullToStr(map.get("nums"));
		
		int nums = 0;
		if(!strNums.equals("")){
			nums = new Integer(strNums).intValue();
		}
		
		hj_nums += nums;
		hj_je += je;
		
%>
		<TR>
			<TD class=ReportItemXh><%=product_id %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_name")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_xh")) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		</TR>
	
<%
	}
}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合计：</TD>
			<TD class=ReportItem>&nbsp;</TD>		
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
		</TR>
		
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击商品名称可查看原始单据列表。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>