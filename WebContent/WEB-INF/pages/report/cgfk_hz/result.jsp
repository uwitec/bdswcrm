<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

CgfkHzReportService cgfkHzReportService = (CgfkHzReportService)VS.findValue("cgfkHzReportService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String jsr = StringUtils.nullToStr(request.getParameter("jsr"));
String account_id = StringUtils.nullToStr(request.getParameter("account_id"));
String isYfk = StringUtils.nullToStr(request.getParameter("isYfk"));

if(isYfk.equals("1")){
	isYfk = "";
}else{
	isYfk = "否";
}
%>

<html>
<head>
<title>采购付款统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url,winTitle){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,winTitle,fea);	
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>采购付款统计</B></font><br>日期：<%=start_date %> 至 <%=end_date %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>客户名称</TD>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>经手人</TD>
			<TD class=ReportHead>付款日期</TD>
			<TD class=ReportHead>付款账户</TD>
			<TD class=ReportHead>是否预付</TD>
			<TD class=ReportHead>金额</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%
List cgfkMxList = cgfkHzReportService.getCgfkMxList(start_date,end_date,client_name,jsr,isYfk,account_id);

double hj_fkje = 0;

if(cgfkMxList != null && cgfkMxList.size() > 0){
	for(int i=0;i<cgfkMxList.size();i++){
		Map map = (Map)cgfkMxList.get(i);


		String clientName = StringUtils.nullToStr(map.get("client_name"));
		String id = StringUtils.nullToStr(map.get("id"));
		String fk_jsr = StringUtils.nullToStr(map.get("jsr"));
		String fk_date = StringUtils.nullToStr(map.get("fk_date"));
		String fkzh = StringUtils.nullToStr(map.get("fkzh"));
		String is_yfk = StringUtils.nullToStr(map.get("is_yfk"));
		double fkje = map.get("fkje")==null?0:((Double)map.get("fkje")).doubleValue();
		
		String remark = StringUtils.nullToStr(map.get("remark"));
		
		if(remark.indexOf("采购退货冲抵往来，退货单编号[") == -1){
			
			hj_fkje += fkje;
			
%>	
		<TR>
			<TD class=ReportItem><%=clientName %></TD>
			<TD class=ReportItem><a style="cursor:hand" onclick="openWin('viewCgfk.html?id=<%=id %>','采购付款');" title="点击查看原始单据"><%=id %></a></TD>
			<TD class=ReportItem><%=StaticParamDo.getRealNameById(fk_jsr) %></TD>
			<TD class=ReportItem><%=fk_date %></TD>
			<TD class=ReportItem><%=StaticParamDo.getAccountNameById(fkzh) %></TD>
			<TD class=ReportItem><%=is_yfk %></TD>		
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(fkje,2) %></TD>
		</TR>

<%
		}
	}
}
%>
	<TR>
		<TD class=ReportItem style="font-weight:bold">合计（金额）</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>		
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_fkje,2) %></TD>

	</TR>
	
	</TBODY>
	
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击商品编号可查看原始单据列表。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>