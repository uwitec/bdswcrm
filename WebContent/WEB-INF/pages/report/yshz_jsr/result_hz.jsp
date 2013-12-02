<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");

Map ysjeMap = (Map)VS.findValue("ysjeMap");
Map cqjeMap = (Map)VS.findValue("cqjeMap");
Map cqtsMap = (Map)VS.findValue("cqtsMap");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>业务员未收单据汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
</head>
<body align="center">
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>业务员未收单据汇总</B></font></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>业务员</TD>
			<TD class=ReportHead>应收金额</TD>
			<TD class=ReportHead>超期应收金额</TD>
			<TD class=ReportHead>平均账期</TD>
			<TD class=ReportHead>最长账期</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%
double hj_ysje = 0;
double hj_cqje = 0;
if(userList != null && userList.size() > 0){
	for(int i=0;i<userList.size();i++){
		SysUser user = (SysUser)userList.get(i);
		
		String user_name = StringUtils.nullToStr(user.getReal_name());
		String user_id = StringUtils.nullToStr(user.getUser_id());
		
		double ysje = ysjeMap.get(user_id)==null?0:((Double)ysjeMap.get(user_id)).doubleValue();  //应收金额		
		double cqje = cqjeMap.get(user_id)==null?0:((Double)cqjeMap.get(user_id)).doubleValue();  //超期应收金额
		
		hj_ysje += ysje;
		hj_cqje += cqje;
		
		String avgzc = StringUtils.nullToStr(cqtsMap.get(user_id+"avg"));  //平均账期
		if(avgzc.equals("")) avgzc = "0";
		double avgzc_num = new Double(avgzc);
		String maxzc = StringUtils.nullToStr(cqtsMap.get(user_id+"max"));  //最长账期
		if(maxzc.equals("")) maxzc = "0";
		if(ysje != 0){
%>	
		<TR>
			<TD class=ReportItemXh><a href="queryywy_wsdj.html?user_id=<%=StringUtils.nullToStr(user_id) %>"><%=user_name %></TD>
			<TD class=ReportItemMoney nowrap="nowrap"><%=JMath.round(ysje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap"><%=JMath.round(cqje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap"><%=JMath.round(avgzc_num,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap"><%=maxzc %>&nbsp;</TD>
		</TR>

<%		
		}
	}
}
%>
	<TR>
		<TD class=ReportItemXh style="font-weight:bold"><B>合计</B></TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_ysje,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_cqje,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap">&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap">&nbsp;</TD>
	</TR>
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
		<td width="70%" height="30">&nbsp;
		</td>
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