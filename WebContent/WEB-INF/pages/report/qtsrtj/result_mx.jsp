<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List resutls = (List)VS.findValue("reustls");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));  //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));      //结束时间

String srlx = StringUtils.nullToStr(request.getParameter("srlx"));        //收入类型


String strCon = "";

strCon = "<b>日期：</b>" + start_date + "至" + end_date;
if(!srlx.equals("")){
	strCon += "&nbsp;&nbsp;<b>收入类型：</b>" + srlx;
}
%>

<html>
<head>
<title>其他收入统计明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function openWin(url){
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
</script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>其他收入统计——明细表</B></font><br><%=strCon %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>编号</TD>
			<TD class=ReportHead>经手人</TD>			
			<TD class=ReportHead>收入日期</TD>
			<TD class=ReportHead>金额</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%
if(resutls != null && resutls.size()>0){
	
	double hj_je = 0;
	
	for(int i=0;i<resutls.size();i++){
		Map map = (Map)resutls.get(i);

		String id = StringUtils.nullToStr(map.get("id"));
		String jsr = StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("jsr")));
		
		String sr_date = StringUtils.nullToStr(map.get("sr_date"));
		
		double je = map.get("skje")==null?0:((Double)map.get("skje")).doubleValue();
		
		hj_je += je;
%>	
		<TR>
			<TD class=ReportItemXh><a href="javascript:openWin('viewQtsr.html?id=<%=id %>');"><%=id %></a>&nbsp;</TD>
			<TD class=ReportItemXh><%=jsr %>&nbsp;</TD>			
			<TD class=ReportItemXh><%=sr_date %>&nbsp;</TD>			
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		</TR>
<%
	}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合计</TD>
			
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
			
		</TR>
<%
}
%>
	</TBODY>
</TABLE>
<table width="99%">
		<tr>
			<td align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>