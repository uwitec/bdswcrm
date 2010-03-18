<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List reustls = (List)VS.findValue("reustls");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));  //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));      //结束时间
String dept = StringUtils.nullToStr(request.getParameter("dept"));        //部门
String fy_type = StringUtils.nullToStr(request.getParameter("fy_type"));        //费用类型


String strCon = "";

strCon = "<b>日期：</b>" + start_date + "至" + end_date;

if(!dept.equals("")){
	strCon += "&nbsp;&nbsp;<b>部门：</b>" + StaticParamDo.getDeptNameById(dept);
}
if(!fy_type.equals("")){
	strCon += "&nbsp;&nbsp;<b>费用类型：</b>" + StaticParamDo.getFyTypeNameById(fy_type);
}
%>

<html>
<head>
<title>一般费用汇总表</title>
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
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>一般费用汇总表</B></font><br><%=strCon %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>序号</TD>
			<TD class=ReportHead>编号</TD>
			<TD class=ReportHead>费用申请人</TD>
			<TD class=ReportHead>费用使用部门</TD>
			<TD class=ReportHead>支出日期</TD>
			<TD class=ReportHead>金额</TD>	
		</TR>
	</THEAD>
	
	<TBODY>
<%
if(reustls != null && reustls.size()>0){
	
	double hj_je = 0;
	
	for(int i=0;i<reustls.size();i++){
		Map map = (Map)reustls.get(i);

		String id = StringUtils.nullToStr(map.get("id"));
		String sqr = StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("sqr")));
		String ywy_dept = StaticParamDo.getDeptNameById(StringUtils.nullToStr(map.get("ywy_dept")));
		String zc_date = StringUtils.nullToStr(map.get("zc_date"));
		double je = map.get("zcje")==null?0:((Double)map.get("zcje")).doubleValue();
		
		hj_je += je;
%>	
		<TR>
			<TD class=ReportItemXh><%=i+1 %>&nbsp;</TD>
			<TD class=ReportItemXh><a href="javascript:openWin('viewQtzc.html?id=<%=id %>');"><%=id %></a>&nbsp;</TD>
			<TD class=ReportItemXh><%=sqr %>&nbsp;</TD>
			<TD class=ReportItemXh><%=ywy_dept %>&nbsp;</TD>
			<TD class=ReportItemXh><%=zc_date %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		</TR>
<%
	}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合计</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
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