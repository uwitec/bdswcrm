<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XstjXsryService xstjXsryService = (XstjXsryService)VS.findValue("xstjXsryService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));  //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));      //结束时间
String dept_id = StringUtils.nullToStr(request.getParameter("dept_id"));        //部门
String user_id = StringUtils.nullToStr(request.getParameter("user_id"));        //业务员编号

List results = xstjXsryService.getYwytcHz(start_date,end_date,dept_id,user_id);


String strCon = "";

strCon = "日期：" + start_date + "至" + end_date;

if(!dept_id.equals("")){
	strCon += "&nbsp;&nbsp;部门：" + StaticParamDo.getDeptNameById(dept_id);
}

if(!user_id.equals("")){
	strCon += "&nbsp;&nbsp;业务员：" + StaticParamDo.getRealNameById(user_id);
}
%>

<html>
<head>
<title>业务员提成汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>业务员提成汇总</B></font><br><%=strCon %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>部门</TD>
			<TD class=ReportHead>业务员姓名</TD>
			<TD class=ReportHead>基本提成</TD>
			<TD class=ReportHead>比例点杀</TD>
			<TD class=ReportHead>金额点杀</TD>
			<TD class=ReportHead>超限奖励</TD>	
			<TD class=ReportHead>提成合计</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%

if(results != null && results.size()>0){
	
	double hj_jbtc = 0;
	double hj_blds = 0;
	double hj_jeds = 0;
	double hj_cxjl = 0;
	double hj_total = 0;
	
	for(int i=0;i<results.size();i++){
		Map map = (Map)results.get(i);
		
		double jbtc = map.get("jbtc") == null?0:((Double)map.get("jbtc")).doubleValue();
		double blds = map.get("blds") == null?0:((Double)map.get("blds")).doubleValue();
		double jeds = map.get("jeds") == null?0:((Double)map.get("jeds")).doubleValue();
		double cxjl = map.get("cxjl") == null?0:((Double)map.get("cxjl")).doubleValue();
		
		if(cxjl < 0) cxjl = 0;
		double total = jbtc + blds + jeds + cxjl;
		
		hj_jbtc += jbtc;
		hj_blds += blds;
		hj_jeds += jeds;
		hj_cxjl += cxjl;
		hj_total += total;

%>	
		<TR>
			<TD class=ReportItemXH><%=StaticParamDo.getDeptNameById((String)map.get("dept")) %>&nbsp;</TD>
			<TD class=ReportItemXH>
				<a href="getYwytcMxResult.html?start_date=<%=start_date %>&end_date=<%=end_date %>&user_id=<%=StringUtils.nullToStr(map.get("xsry")) %>"><%=StringUtils.nullToStr(map.get("real_name")) %></a>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(jbtc,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(blds,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(jeds,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(cxjl,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(total,2) %>&nbsp;</TD>
		</TR>
<%
	}
%>
		<TR>
			<TD class=ReportItem style="font-weight:bold">合计</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_jbtc,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_blds,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_jeds,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_cxjl,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_total,2) %>&nbsp;</TD>
		</TR>
<%
}
%>
	</TBODY>
</TABLE>
<table width="99%">
		<tr>
			<td width="70%" height="30" valign="top">注：<BR>&nbsp;&nbsp;1、基本提成=考核毛利*基本提成比例；
                                           <BR>&nbsp;&nbsp;2、比例点杀=考核毛利*比例点杀;
                                           <BR>&nbsp;&nbsp;3、超限奖励=(不含税金额-零售限价)*超限提成比例;
                                           <BR>点击业务员姓名可以查看明细。</td>
			<td align="right" height="30" valign="top">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>