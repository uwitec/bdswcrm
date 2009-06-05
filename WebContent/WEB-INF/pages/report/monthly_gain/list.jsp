<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>月度利润表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
</head>
<body align="center" >
<ww:form name="myform" action="getMothlyGainList" method="post" theme="simple">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>月度利润表</B></font><br>
		    <ww:select name="year" id="year" value="%{year}" emptyOption="false" list="#{'2007':'2007','2008':'2008','2009':'2009','2010':'2010','2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015'}" theme="simple"/>
		    <ww:select name="month" id="month" value="%{month}" emptyOption="false" list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}" theme="simple"/>
		    <ww:submit name="subBtn" id="subBtn" value=" 统计 "  theme="simple"/>
		    </TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead width="20%">序号</TD>
			<TD class=ReportHead width="50%">会计科目</TD>
			<TD class=ReportHead width="30%">金额</TD>
		</TR>
	</THEAD>
	<TBODY>
	<TR>
		<TD class=ReportItemXh>一</TD>
		<TD class=ReportItemXh>毛利</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{gross})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItemXh>二</TD>
		<TD class=ReportItemXh>其它收入</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{income})}"/>&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItemXh>三</TD>
		<TD class=ReportItemXh>费用</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{cost})}"/>&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItemXh>四</TD>
		<TD class=ReportItemXh>待摊费用</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{deferredPayment})}"/>&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItemXh>五</TD>
		<TD class=ReportItemXh>商品报溢收入</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{goodbysr})}"/>&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItemXh>六</TD>
		<TD class=ReportItemXh>商品报损支出</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{goodbszc})}"/>&nbsp;</TD>
	</TR>
	<TR>						
	<TR>
		<TD class=ReportItemXh style="font-weight:bold">七</TD>
		<TD class=ReportItemXh style="font-weight:bold">合 计</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><ww:property value="%{getText('global.format.money',{gross+income+cost+deferredPayment+goodbysr+goodbszc})}"/>&nbsp;</TD>
	</TR>
	</TBODY>
</TABLE>
</ww:form>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();">
</center>
</body>
</html>