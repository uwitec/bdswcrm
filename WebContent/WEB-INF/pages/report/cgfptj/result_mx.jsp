<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

CgfptjService cgfptjService = (CgfptjService)VS.findValue("cgfptjService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String con = "";
con = "日期：" + start_date + "至" + end_date;
if(!client_name.equals("")){
	con += "&nbsp;&nbsp; 客户名称：" + StaticParamDo.getClientNameById(client_name);
} 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>采购发票统计－－明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
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
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>采购发票统计－－明细</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>采购时间</TD>
			<TD class=ReportHead>说明/单据号</TD>
			<TD class=ReportHead>发生金额</TD>
			<TD class=ReportHead>发票状态</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
double hj_fp = 0; //合计发票金额	
List list = cgfptjService.getCgfpSeqList(client_name,start_date,end_date);
if(list != null && list.size()>0)
{
	for(int i=0;i<list.size();i++)
	{
		Map map = (Map)list.get(i);
		String cg_date = StringUtils.nullToStr(map.get("cg_date"));
		String jhd_id = StringUtils.nullToStr(map.get("jhd_id"));
		String state = StringUtils.nullToStr(map.get("state"));
		
		double total = map.get("total")==null?0:((Double)map.get("total")).doubleValue();
%>
		<TR>
			<TD class=ReportItemXh><%=cg_date %></TD>
			<TD class=ReportItemXh><a href="#"  onclick="openWin('viewJhd.html?id=<%=jhd_id %>','采购订单');" title="点击查看原始单据"><%=jhd_id %></a></TD>
			<TD class=ReportItemMoney><%=JMath.round(total,2) %></TD>
			<TD class=ReportItemXh><%=state %></TD>
		</TR>
	
<%
   hj_fp+=total;
		}
	}
%>
			<TR>
				<TD class=ReportItemXh style="font-weight:bold">合计</TD>
				<TD class=ReportItem>&nbsp;</TD>
				
				<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_fp,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney>&nbsp;</TD>
			</TR>

	</TBODY>
</TABLE>
<table width="100%">
		<tr>
			<td width="70%" height="30">注：点击单据号可以查看原始单据。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<br>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>