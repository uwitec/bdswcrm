<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List khjlList = (List)VS.findValue("khjlList");

Map qcMap = (Map)VS.findValue("qcMap");
Map fsMap = (Map)VS.findValue("fsMap");

String start_date = StringUtils.nullToStr(VS.findValue("start_date"));   //开始时间
String end_date = StringUtils.nullToStr(VS.findValue("end_date"));       //结束时间
String client_name = StringUtils.nullToStr(VS.findValue("client_name")); //客户名称
String khjl = StringUtils.nullToStr(VS.findValue("khjl"));                 //经手人

String con = "日期：" + start_date + "至" + end_date;
if(!client_name.equals("")){
	con += "　客户名称：" + StaticParamDo.getClientNameById(client_name);
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户经理应收汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<style media=print>  
	.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
</head>
<body align="center">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户经理应收汇总</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>客户经理</TD>
			<TD class=ReportHead>期初数</TD>
			<TD class=ReportHead>本期发生数</TD>
			<TD class=ReportHead>本期收款</TD>
			<TD class=ReportHead>当前应收款</TD>
			<TD class=ReportHead>回款率</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%

double hj_qcs = 0;
double hj_bqfs = 0;
double hj_bqsk = 0;
double hj_dqys = 0;

if(khjlList != null && khjlList.size() > 0){
	for(int i=0;i<khjlList.size();i++){
		Map khjlMap = (Map)khjlList.get(i);
		
		String user_name = StringUtils.nullToStr(khjlMap.get("real_name"));
		String user_id = StringUtils.nullToStr(khjlMap.get("user_id"));
		
		double qcs = qcMap.get(user_id)==null?0:((Double)qcMap.get(user_id)).doubleValue();  //期初数
		
		double bqfs = fsMap.get(user_id+"应收发生")==null?0:((Double)fsMap.get(user_id+"应收发生")).doubleValue();  //本期发生
		
		double bqsk = fsMap.get(user_id+"已收发生")==null?0:((Double)fsMap.get(user_id+"已收发生")).doubleValue();  //本期已收
		
		double dqys = qcs + bqfs - bqsk;
		
		if(dqys != 0){
			hj_qcs += qcs;
			hj_bqfs += bqfs;
			hj_bqsk += bqsk;
			hj_dqys += dqys;
%>	
		<TR>
			<TD class=ReportItemXh><a href="getYshzKhjlResultMx.html?start_date=<%=start_date %>&end_date=<%=end_date %>&client_name=<%=client_name %>&khjl=<%=user_id %>"><%=user_name %></a></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="期初数"><%=JMath.round(qcs,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="本期发生数"><%=JMath.round(bqfs,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="本期收款"><%=JMath.round(bqsk,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="当前应收款"><%=JMath.round(dqys,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="回款率"><%=JMath.percent(bqsk,bqfs) %>&nbsp;</TD>
		</TR>

<%		
		}
	}
}
%>
	<TR>
		<TD class=ReportItemXh style="font-weight:bold">合计（金额）</TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_qcs,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_bqfs,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_bqsk,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_dqys,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.percent(hj_bqsk,hj_bqfs) %>&nbsp;</TD>
	</TR>
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
		<td width="70%" height="30">&nbsp;注：回款率=本期收款/本期发生数*100%
		</td>
		<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>