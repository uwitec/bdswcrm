<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XstjClientService xstjClientService = (XstjClientService)VS.findValue("xstjClientService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));   //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));       //截止时间
String xsry_id = StringUtils.nullToStr(request.getParameter("xsry_id"));         //销售人员编号
String dj_id = StringUtils.nullToStr(request.getParameter("dj_id"));             //单据编号
String isShowZ = StringUtils.nullToStr(request.getParameter("isShowZ"));         //是否显示销售额为零客户
String client_name = StringUtils.nullToStr(request.getParameter("client_name")); 

//汇总结果
List statResult = (List)VS.findValue("statResult");


%>

<html>
<head>
<title>客户销售汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(client_name){
		location.href = "getXstjClientResultMx.html?start_date=<%=start_date%>&end_date=<%=end_date%>&xsry_id=<%=xsry_id%>&dj_id=<%=dj_id%>&client_name=" + client_name;
	}
	
	function openWinLs(){
		location.href = "getXstjClientResultLsMx.html?start_date=<%=start_date%>&end_date=<%=end_date%>&xsry_id=<%=xsry_id%>&dj_id=<%=dj_id%>";	
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户销售汇总</B></font><br>日期：<%=start_date %> 至 <%=end_date %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>客户编号</TD>
			<TD class=ReportHead>客户名称</TD>
			<TD class=ReportHead>总金额</TD>	
		</TR>
	</THEAD>
	<TBODY>
<%
double hjje = 0;
if(statResult != null && statResult.size()>0){
	for(int i=0;i<statResult.size();i++){
		Map map = (Map)statResult.get(i);
		
		String id = StringUtils.nullToStr(map.get("client_id"));
		String name = StringUtils.nullToStr(map.get("client_name"));
		
		double zje = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		
		boolean is = false;
		if(isShowZ.equals("否")){
			if(zje != 0){
				is = true;
			}
		}else{
			is = true;
		}
		
		if(is){
			hjje += zje;
%>
	<TR>
		<TD class=ReportItem><%=id %>&nbsp;</TD>
		<TD class=ReportItem><a href="javascript:openWin('<%=id %>');"><%=name %></a>&nbsp;</TD>
		<TD class=ReportItemMoney><%=JMath.round(zje,2) %>&nbsp;</TD>
	</TR>
<%
		}
	}
}

if(client_name.equals("")){

	double lsdzje = xstjClientService.getLsdZje(start_date, end_date, xsry_id, dj_id);
	hjje += lsdzje;
%>

	<TR>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem><a href="javascript:openWinLs();">零售单总计</a>&nbsp;</TD>
		<TD class=ReportItemMoney><%=JMath.round(lsdzje,2) %>&nbsp;</TD>
	</TR>
<%
}
%>

	<TR>
		<TD class=ReportItem style="font-weight:bold">合计：</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hjje,2) %>&nbsp;</TD>
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