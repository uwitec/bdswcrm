<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
			<TD class=ReportHead width="35%">项   目</TD>
			<TD class=ReportHead width="15%">行   次</TD>
			<TD class=ReportHead width="25%">本 月 数 （元）</TD>
			<TD class=ReportHead width="25%">本年累积数 （元）</TD>
		</TR>
	</THEAD>
	<TBODY>
	<TR>
		<TD class=ReportItem>　　<B>一、主营业务收入</B></TD>
		<TD class=ReportItemXH><B>1</B></TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{zyywsrMap['curMonth']})}" /></B>&nbsp;</TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{zyywsrMap['allMonth']})}" /></B>&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　减：主营业务成本</TD>
		<TD class=ReportItemXH>2</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{zyywcbMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{zyywcbMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　<B>二、主营业务利润(亏损以"-"号填列)</B></TD>
		<TD class=ReportItemXH><B>3</B></TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{zyywsrMap['curMonth']-zyywcbMap['curMonth']})}" /></B>&nbsp;</TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{zyywsrMap['allMonth']-zyywcbMap['allMonth']})}" /></B>&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　加：其它业务收入</TD>
		<TD class=ReportItemXH>4</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{qtywsrMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{qtywsrMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　减：其它业务成本</TD>
		<TD class=ReportItemXH>5</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{qtywcbMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{qtywcbMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　<B>三、营业利润（亏损以"-"号填列）</B></TD>
		<TD class=ReportItemXH><B>6</B></TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{zyywsrMap['curMonth']-zyywcbMap['curMonth']+qtywsrMap['curMonth']-qtywcbMap['curMonth']})}" /></B>&nbsp;</TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{zyywsrMap['curMonth']-zyywcbMap['curMonth']+qtywsrMap['allMonth']-qtywcbMap['allMonth']})}" /></B>&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　加：营业外收入</TD>
		<TD class=ReportItemXH>7</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{yywsrMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{yywsrMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　　　商品报溢收入</TD>
		<TD class=ReportItemXH>8</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{spbysrMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{spbysrMap['allMonth']})}" />&nbsp;</TD>
	</TR>	
	<TR>
		<TD class=ReportItem>　　　　　　往来调帐收入</TD>
		<TD class=ReportItemXH>9</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{wltzsrMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{wltzsrMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　减：营业外支出</TD>
		<TD class=ReportItemXH>10</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{yywzcMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{yywzcMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　　　商品报损支出</TD>
		<TD class=ReportItemXH>11</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{spbszcMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{spbszcMap['allMonth']})}" />&nbsp;</TD>
	</TR>	
	<TR>
		<TD class=ReportItem>　　　　　　往来调账支出</TD>
		<TD class=ReportItemXH>12</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{wltzzcMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{wltzzcMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　　　待摊费用</TD>
		<TD class=ReportItemXH>13</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{dtfyMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{dtfyMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　<B>四、利润总额（亏损总额以"-"号填列）</B></TD>
		<TD class=ReportItemXH><B>14</B></TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{zyywsrMap['curMonth']-zyywcbMap['curMonth']+qtywsrMap['curMonth']-qtywcbMap['curMonth']+yywsrMap['curMonth']+spbysrMap['curMonth']+wltzsrMap['curMonth']-yywzcMap['curMonth']-spbszcMap['curMonth']-wltzzcMap['curMonth']-dtfyMap['curMonth']})}" /></B>&nbsp;</TD>
		<TD class=ReportItemMoney><b><ww:property value="%{getText('global.format.money',{zyywsrMap['allMonth']-zyywcbMap['allMonth']+qtywsrMap['allMonth']-qtywcbMap['allMonth']+yywsrMap['allMonth']+spbysrMap['allMonth']+wltzsrMap['allMonth']-yywzcMap['allMonth']-spbszcMap['allMonth']-wltzzcMap['allMonth']-dtfyMap['allMonth']})}" /></b>&nbsp;</TD>
	</TR>
	</TBODY>
</TABLE>
</ww:form>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();">
</center>
</body>
</html>