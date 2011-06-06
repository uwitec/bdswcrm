<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

CashBankService cashBankService = (CashBankService)VS.findValue("cashBankService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String account_id = StringUtils.nullToStr(request.getParameter("account_id"));
String account_type = StringUtils.nullToStr(request.getParameter("account_type"));

List accountList = cashBankService.getAccountList(account_id,account_type);
Map qcMap = cashBankService.getBankQcByBatch(start_date);
Map jyzcMap = cashBankService.getAccountSeqBatch(start_date,end_date,0);
Map jysrMap = cashBankService.getAccountSeqBatch(start_date,end_date,1);

String conStr = "时间：" + start_date + " 至 " + end_date;
if(!account_id.equals("")){
	conStr += "&nbsp;&nbsp;账户名称：" + StaticParamDo.getAccountNameById(account_id);
}

if(!account_type.equals("")){
	conStr += "&nbsp;&nbsp;账户类型：" + account_type;
}
%>

<html>
<head>
<title>现金银行汇总表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url){
		var fea ='width=750,height=500,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>现金银行汇总表</B></font><br><%=conStr %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>账户编号</TD>
			<TD class=ReportHead>账户名称</TD>
			<TD class=ReportHead>账户类型</TD>
			<TD class=ReportHead>期初</TD>
			<TD class=ReportHead>收入金额</TD>
			<TD class=ReportHead>支出金额</TD>
			<TD class=ReportHead>余额</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
if(accountList != null && accountList.size()>0){
	
	double hj_qc_je = 0;
	double hj_zcje = 0;
	double hj_srje = 0;
	double hj_ye = 0;
	
	for(int i=0;i<accountList.size();i++){
		
		Map map = (Map)accountList.get(i);
		
		String id = StringUtils.nullToStr(map.get("id"));
		String name = StringUtils.nullToStr(map.get("name"));
		String type = StringUtils.nullToStr(map.get("type"));

		double qc_je = 0;  //期初金额
		double zcje = 0;   //支出金额
		double srje = 0;   //收入金额
		
		qc_je = qcMap.get(id)==null?0:((Double)qcMap.get(id)).doubleValue();
		srje =  jysrMap.get(id)==null?0:((Double)jysrMap.get(id)).doubleValue();
		zcje =  0-(jyzcMap.get(id)==null?0:((Double)jyzcMap.get(id)).doubleValue());

		hj_qc_je += qc_je;
		hj_zcje += zcje;
		hj_srje += srje;
		double ye = qc_je + srje - zcje;  //余额
		
		hj_ye += ye;
%>
		<TR>
			<TD class=ReportItemXh><%=id %>&nbsp;</TD>
			<TD class=ReportItem><a href="getCashBankResultMx.html?start_date=<%=start_date %>&end_date=<%=end_date %>&account_id=<%=id %>"><%=name %></a>&nbsp;</TD>
			<TD class=ReportItemXh><%=type %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(qc_je,2) %>&nbsp;</TD>
			
			<TD class=ReportItemMoney><%=JMath.round(srje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(zcje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ye,2) %>&nbsp;</TD>
		</TR>
<%		
	}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合计&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_qc_je,2) %>&nbsp;</TD>
			
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_srje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_zcje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_ye,2) %>&nbsp;</TD>
		</TR>
<%
}
%>

	</TBODY>
</TABLE>
<table width="100%">
		<tr>
			<td width="70%" height="30">注：点击账户名称可以查看明细信息。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<br>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showCashBankCondition.html';"> 
</center>
</body>
</html>