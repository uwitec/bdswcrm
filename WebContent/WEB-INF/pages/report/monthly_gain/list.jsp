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
		<TD class=ReportItem>　　　　减：主营业务税金及附加</TD>
		<TD class=ReportItemXH>3</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{zyywsjjfjMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{zyywsjjfjMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<ww:set name="zyxwlrCur" value="zyywsrMap['curMonth']-zyywcbMap['curMonth']-zyywsjjfjMap['curMonth']"/>
	<ww:set name="zyxwlrAll" value="zyywsrMap['allMonth']-zyywcbMap['allMonth']-zyywsjjfjMap['allMonth']"/>
	<TR>
		<TD class=ReportItem>　　<B>二、主营业务利润(亏损以"-"号填列)</B></TD>
		<TD class=ReportItemXH><B>4</B></TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{#zyxwlrCur})}" /></B>&nbsp;</TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{#zyxwlrAll})}" /></B>&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　加：其它业务利润</TD>
		<TD class=ReportItemXH>5</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{qtywsrMap['curMonth']-qtywcbMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{qtywsrMap['allMonth']-qtywcbMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　减：营业费用</TD>
		<TD class=ReportItemXH>6</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{yywzcMap['curMonth']+dtfyMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{yywzcMap['allMonth']+dtfyMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　减：财务费用</TD>
		<TD class=ReportItemXH>7</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{cwfyMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{cwfyMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　减：管理费用</TD>
		<TD class=ReportItemXH>8</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{glfyMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{glfyMap['allMonth']})}" />&nbsp;</TD>
	</TR>		
	<ww:set name="yylrCur" value="#zyxwlrCur+qtywsrMap['curMonth']-qtywcbMap['curMonth']-yywzcMap['curMonth']-dtfyMap['curMonth']-cwfyMap['curMonth']-glfyMap['curMonth']"/>
	<ww:set name="yylrAll" value="#zyxwlrAll+qtywsrMap['allMonth']-qtywcbMap['allMonth']-yywzcMap['allMonth']-dtfyMap['allMonth']-cwfyMap['allMonth']-glfyMap['allMonth']"/>			
	<TR>
		<TD class=ReportItem>　　<B>三、营业利润（亏损以"-"号填列）</B></TD>
		<TD class=ReportItemXH><B>9</B></TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{#yylrCur})}" /></B>&nbsp;</TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{#yylrAll})}" /></B>&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　加：营业外收入</TD>
		<TD class=ReportItemXH>10</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{yywsrMap['curMonth']+spbysrMap['curMonth']+wltzsrMap['curMonth']+tjlrMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{yywsrMap['allMonth']+spbysrMap['allMonth']+wltzsrMap['allMonth']+tjlrMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　减：营业外支出</TD>
		<TD class=ReportItemXH>11</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{spbszcMap['curMonth']+wltzzcMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{spbszcMap['allMonth']+wltzzcMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<ww:set name="lrzeCur" value="#yylrCur+yywsrMap['curMonth']+spbysrMap['curMonth']+wltzsrMap['curMonth']+tjlrMap['curMonth']-spbszcMap['curMonth']-wltzzcMap['curMonth']"/>
	<ww:set name="lrzeAll" value="#yylrAll+yywsrMap['allMonth']+spbysrMap['allMonth']+wltzsrMap['allMonth']+tjlrMap['allMonth']-spbszcMap['allMonth']-wltzzcMap['allMonth']"/>		
	<TR>
		<TD class=ReportItem>　　<B>四、利润总额（亏损总额以"-"号填列）</B></TD>
		<TD class=ReportItemXH><B>12</B></TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{#lrzeCur})}" /></B>&nbsp;</TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{#lrzeAll})}" /></B>&nbsp;</TD>
	</TR>
	<TR>
		<TD class=ReportItem>　　　　减：所得税</TD>
		<TD class=ReportItemXH>13</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{sdsMap['curMonth']})}" />&nbsp;</TD>
		<TD class=ReportItemMoney><ww:property value="%{getText('global.format.money',{sdsMap['allMonth']})}" />&nbsp;</TD>
	</TR>
	<ww:set name="jlrCur" value="#lrzeCur-sdsMap['curMonth']"/>
	<ww:set name="jlrAll" value="#lrzeAll-sdsMap['allMonth']"/>			
	<TR>
		<TD class=ReportItem>　　<B>五、净利润（亏损以"-"号填列）</B></TD>
		<TD class=ReportItemXH><B>14</B></TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{#jlrCur})}" /></B>&nbsp;</TD>
		<TD class=ReportItemMoney><B><ww:property value="%{getText('global.format.money',{#jlrAll})}" /></B>&nbsp;</TD>
	</TR>		
	</TBODY>
</TABLE>
</ww:form>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();">
</center>
　注：<BR>
　一、主营业务收入 ＝ 库存商品销售收入 － 库存商品退货金额<BR>
　二、主营业务成本 ＝ 销售所有库存商品合计采购成本（销售 － 退货） <BR>
　三、其它业务利润 ＝ 服务类商品销售 － 服务类商品采购<BR>
　四、营业费用 ＝ 营业费用 ＋ 摊销付款<BR>
　五、营业外收入 ＝ 其它收入 ＋ 商品报溢收入 ＋ 往来调账收入 ＋ 存货调价收入<BR>
　六、营业外支出 ＝ 商品报损支出 ＋ 往来调账支出<BR>
</body>
</html>