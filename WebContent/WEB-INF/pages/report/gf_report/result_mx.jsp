<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List resultMxList = (List)VS.findValue("resultMxList");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));  //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));      //结束时间
String xsry_id = StringUtils.nullToStr(request.getParameter("xsry_id"));        //业务员编号


String strCon = "";

strCon = "<b>日期：</b>" + start_date + "至" + end_date;

if(!xsry_id.equals("")){
	strCon += "&nbsp;&nbsp;<b>业务员：</b>" + StaticParamDo.getRealNameById(xsry_id);
}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>零售工分统计——明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function openWin(url){
		var fea ='width=850,height=650,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'单据详细信息',fea);	
	}
</script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
</head>
<body align="center" >
<form name="reportForm" action="">
	<input type="hidden" name="start_date" value="<%=start_date %>">
	<input type="hidden" name="end_date" value="<%=end_date %>">
</form>
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>零售工分统计——明细</B></font><br><%=strCon %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead width="10%">序号</TD>
			<TD class=ReportHead width="20%">单据编号</TD>
			<TD class=ReportHead width="30%">客户名称</TD>
			<TD class=ReportHead width="25%">发生日期</TD>
			<TD class=ReportHead width="15%">工分</TD>	
		</TR>
	</THEAD>
	
	<TBODY>
<%
if(resultMxList != null && resultMxList.size()>0){
	
	double gf_hj = 0;
	
	for(int i=0;i<resultMxList.size();i++){
		Map map = (Map)resultMxList.get(i);

		String id = StringUtils.nullToStr(map.get("id"));
		String client_name = StringUtils.nullToStr(map.get("client_name"));
		String creatdate = StringUtils.nullToStr(map.get("creatdate"));
		double gf = map.get("gfhj")==null?0:((Double)map.get("gfhj")).doubleValue();
		
		gf_hj += gf;
%>	
		<TR>
			<TD class=ReportItemXh><%=i+1 %>&nbsp;</TD>
			<TD class=ReportItemXh><a href="javascript:openWin('viewLsd.html?id=<%=id %>');"><%=id %></a>&nbsp;</TD>
			<TD class=ReportItemXh><%=client_name %>&nbsp;</TD>
			<TD class=ReportItemXh><%=creatdate %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(gf) %>&nbsp;</TD>
		</TR>
<%
	}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合计</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(gf_hj) %>&nbsp;</TD>
		</TR>
<%
}
%>
	</TBODY>
</TABLE>
<table width="99%">
		<tr>
			<td align="left" height="30">&nbsp;点击单据编号显示原始单据&nbsp;&nbsp;</td>
			<td align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>