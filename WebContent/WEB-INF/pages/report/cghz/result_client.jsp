<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

CghzService cghzService = (CghzService)VS.findValue("cghzService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String dj_id = StringUtils.nullToStr(request.getParameter("dj_id"));

String con = "";
con = "日期：" + start_date + "至" + end_date;

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户采购汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(dj_id,start_date,end_date,client_name){
		//var fea ='width=' + screen.availWidth + ',height=' + (screen.availHeight-25) + ',left=0,top=0,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		//var url = "getClientcgMxCondition.html?dj_id=" + dj_id + "&start_date=" + start_date + "&end_date=" + end_date + "&client_name=" + client_name;
		//window.open(url,'详细信息',fea);
		location.href = "getClientcgMxCondition.html?dj_id=" + dj_id + "&start_date=" + start_date + "&end_date=" + end_date + "&client_name=" + client_name;
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户采购汇总</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>客户编号</TD>
			<TD class=ReportHead>客户名称</TD>
			<TD class=ReportHead>联系人</TD>
			<TD class=ReportHead>联系电话</TD>
			<TD class=ReportHead>金额</TD>		
		</TR>
	</THEAD>
	<TBODY>
<%
List list = cghzService.getClientCgList(start_date, end_date, dj_id, client_name);
if(list != null && list.size()>0){
	
	double hj_je = 0;
	
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		String client_id = StringUtils.nullToStr(map.get("client_id"));
		String name = StringUtils.nullToStr(map.get("client_name"));
		String lxr = StringUtils.nullToStr(map.get("lxr"));
		String lxdh = StringUtils.nullToStr(map.get("lxdh"));
		double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();

		hj_je += je;
%>
		<TR>
			<TD class=ReportItem><%=client_id %>&nbsp;</TD>
			<TD class=ReportItem><a href="#" onclick="openWin('<%=dj_id %>','<%=start_date %>','<%=end_date %>','<%=client_id %>');"><%=name %></a>&nbsp;</TD>			
			<TD class=ReportItem><%=lxr %>&nbsp;</TD>
			<TD class=ReportItem><%=lxdh %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		</TR>
<%
	}
%>
		<TR>
			<TD class=ReportItem style="font-weight:bold">合计：</TD>
			<TD class=ReportItem>&nbsp;</TD>			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
		</TR>
<%
}
%>
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击客户名称可查看原始单据列表。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>