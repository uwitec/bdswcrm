<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List resultList = (List)VS.findValue("resultList");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String dept = StringUtils.nullToStr(request.getParameter("dept"));
String kind_name = StringUtils.nullToStr(request.getParameter("kind_name"));
String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
String product_name = StringUtils.nullToStr(request.getParameter("product_name"));

String con = "";
con = "日期：" + start_date + "至" + end_date;
if(!client_name.equals("")){
	con += "&nbsp; 客户名称：" + StaticParamDo.getClientNameById(client_name);
}
if(!dept.equals("")){
	con += "&nbsp; 部门名称：" + StaticParamDo.getDeptNameById(dept);
}
if(!kind_name.equals("")){
	con += "&nbsp;商品类别：" + kind_name;
}
if(!product_name.equals("")){
	con += "&nbsp;商品名称：" + product_name;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>部门销售汇总--明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script language="javascript" src="print/LodopFuncs.js"></script>
<script language="javascript" src="print/print.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(xsry){
		document.refreshForm.xsry.value = xsry;
		document.refreshForm.submit();
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<form name="refreshForm" action="getDeptxsProductMxResult.html" method="post">
<input type="hidden" name="xsry" value="">
<input type="hidden" name="client_name" value="<%=client_name %>">
<input type="hidden" name="start_date" value="<%=start_date %>">
<input type="hidden" name="end_date" value="<%=end_date %>">
<input type="hidden" name="kind_name" value="<%=kind_name %>">
<input type="hidden" name="product_kind" value="<%=product_kind %>">
<input type="hidden" name="product_name" value="<%=product_name %>">
</form>
<div id="printDIV">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>部门销售汇总--明细</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>销售人员编号</TD>
			<TD class=ReportHead>销售人员姓名</TD>
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
		
		String xsry_id = StringUtils.nullToStr(map.get("xsry"));
		String xsry_name = StringUtils.nullToStr(map.get("real_name"));
		
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
			<TD class=ReportItemXh><%=xsry_id %>&nbsp;</TD>
			<TD class=ReportItemXh><a href="javascript:openWin('<%=xsry_id %>');"><%=xsry_name %></a>&nbsp;</TD>
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
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
		</TR>
		
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
</div>
<center class="Noprint">
    <input type="button" name="button_printsetup" value=" 打印预览 "  onclick="printPre('1','A4','printDIV');"> &nbsp;&nbsp;
	<input type="button" name="button_print" value=" 打 印 " onclick="printReport('部门销售汇总明细表','1','A4','printDIV');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>