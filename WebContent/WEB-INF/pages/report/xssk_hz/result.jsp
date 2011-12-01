<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XsskHzReportService xsskHzReportService = (XsskHzReportService)VS.findValue("xsskHzReportService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String jsr = StringUtils.nullToStr(request.getParameter("jsr"));
String account_id = StringUtils.nullToStr(request.getParameter("account_id"));
String isYsk = StringUtils.nullToStr(request.getParameter("isYsk"));

if(isYsk.equals("1")){
	isYsk = "";
}else{
	isYsk = "否";
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售收款统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
	function openWin(url,winTitle){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,winTitle,fea);	
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>销售收款统计</B></font><br>日期：<%=start_date %> 至 <%=end_date %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>客户名称</TD>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>备注</TD>
			<TD class=ReportHead>经手人</TD>
			<TD class=ReportHead>收款日期</TD>
			<TD class=ReportHead>收款账户</TD>
			<TD class=ReportHead>是否预收</TD>
			<TD class=ReportHead>收款金额</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%
List xsskMxList = xsskHzReportService.getXsskMx(start_date,end_date,client_name,jsr,isYsk,account_id);

double hj_skje = 0;

if(xsskMxList != null && xsskMxList.size() > 0){
	for(int i=0;i<xsskMxList.size();i++){
		Map map = (Map)xsskMxList.get(i);


		String clientName = StringUtils.nullToStr(map.get("client_name"));
		String id = StringUtils.nullToStr(map.get("id"));
		String sk_jsr = StringUtils.nullToStr(map.get("jsr"));
		String sk_date = StringUtils.nullToStr(map.get("sk_date"));
		String skzh = StringUtils.nullToStr(map.get("skzh"));
		String is_ysk = StringUtils.nullToStr(map.get("is_ysk"));
		double skje = map.get("skje")==null?0:((Double)map.get("skje")).doubleValue();
		
		String remark = StringUtils.nullToStr(map.get("remark"));
		
		if(remark.indexOf("退货款冲抵往来，退货单编号[") == -1){
			
			hj_skje += skje;
			
%>	
		<TR>
			<TD class=ReportItem><%=StaticParamDo.getClientNameById(clientName) %>&nbsp;</TD>
			<TD class=ReportItem><a href="#" onclick="openWin('viewXssk.html?id=<%=id %>','销售收款');" title="点击查看原始单据"><%=id %></a>&nbsp;</TD>
			<TD class=ReportItem><%=remark %>&nbsp;</TD>
			<TD class=ReportItem><%=StaticParamDo.getRealNameById(sk_jsr) %>&nbsp;</TD>
			<TD class=ReportItem><%=sk_date %>&nbsp;</TD>
			<TD class=ReportItem><%=StaticParamDo.getAccountNameById(skzh) %>&nbsp;</TD>
			<TD class=ReportItem><%=is_ysk %>&nbsp;</TD>		
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(skje,2) %>&nbsp;</TD>
		</TR>

<%
		}
	}
}
%>
	<TR>
		<TD class=ReportItem style="font-weight:bold">合计（金额）&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>		
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_skje,2) %>&nbsp;</TD>

	</TR>
	</TBODY>
	
</TABLE>
<br>
<table width="99%">
	<tr>
		<td width="70%" height="30">&nbsp;说明：客户编号或客户名称打开应付对账单</td>
		<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>