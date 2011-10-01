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

String conStr = "时间：" + start_date + " 至 " + end_date;
if(!account_id.equals("")){
	conStr += "&nbsp;&nbsp;账户名称：" + StaticParamDo.getAccountNameById(account_id);
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>现金银行汇总表－－明细</B></font><br><%=conStr %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>交易时间</TD>
			<TD class=ReportHead>说明/单据号</TD>
			<TD class=ReportHead>经手人</TD>
			<TD class=ReportHead>收入金额</TD>
			<TD class=ReportHead>支出金额</TD>
			<TD class=ReportHead>账户余额</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
	//期初金额
	double qc_je = 0l;
	
	String qc_date = "";
	Map qc_map = cashBankService.getCashBankQc(start_date,account_id);
	if(qc_map != null){
		qc_je = qc_map.get("qcje")==null?0:((Double)qc_map.get("qcje")).doubleValue();
		qc_date = StringUtils.nullToStr(qc_map.get("qc_date"));
	}
%>
		<TR>
			<TD class=ReportItemXh><%=qc_date %>&nbsp;</TD>
			<TD class=ReportItem>期初金额</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(qc_je,2) %>&nbsp;</TD>
		</TR>
	
<%
	double hj_zcje = 0;   //支出金额
	double hj_srje = 0;   //收入金额
	double ye = qc_je;        //余额

	List mxList = cashBankService.getAccountSeqList(account_id,start_date,end_date);
	
	if(mxList != null && mxList.size()>0){
		for(int k=0;k<mxList.size();k++){
			Map mxMap = (Map)mxList.get(k);	
			
			String cdate = StringUtils.nullToStr(mxMap.get("jy_date"));
			if(cdate.length()>=19){
				cdate = cdate.substring(0,19);
			}
			String remark = StringUtils.nullToStr(mxMap.get("remark"));
			String jsr = StaticParamDo.getRealNameById(StringUtils.nullToStr(mxMap.get("jsr")));
			
			//交易金额
			double jyje = mxMap.get("jyje")==null?0:((Double)mxMap.get("jyje")).doubleValue();
			ye += jyje;
			
			if(jyje > 0){ //收入
				hj_srje += jyje;
%>
			<TR>
				<TD class=ReportItemXh><%=cdate %>&nbsp;</TD>
				<TD class=ReportItem><a href="javascript:openWin('<%=StringUtils.nullToStr(mxMap.get("action_url")) %>');" title="点击查看原始单据"><%=remark %>&nbsp;</a></TD>
				<TD class=ReportItemXh><%=jsr %>&nbsp;</TD>
				
				<TD class=ReportItemMoney><%=JMath.round(jyje,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney>&nbsp;</TD>
				<TD class=ReportItemMoney><%=JMath.round(ye,2) %>&nbsp;</TD>
			</TR>
<%
				
			}else{   //支出
				hj_zcje += (0-jyje);
%>
			<TR>
				<TD class=ReportItemXh><%=cdate %>&nbsp;</TD>
				<TD class=ReportItem><a href="javascript:openWin('<%=StringUtils.nullToStr(mxMap.get("action_url")) %>');" title="点击查看原始单据"><%=remark %>&nbsp;</a></TD>
				<TD class=ReportItemXh><%=jsr %>&nbsp;</TD>
				
				<TD class=ReportItemMoney>&nbsp;</TD>
				<TD class=ReportItemMoney><%=JMath.round(0-jyje,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=JMath.round(ye,2) %>&nbsp;</TD>
			</TR>
<%				
			}	
		}
	}
%>
			<TR>
				<TD class=ReportItemXh style="font-weight:bold">合计</TD>
				<TD class=ReportItem>&nbsp;</TD>
				<TD class=ReportItem>&nbsp;</TD>
				
				<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_srje,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_zcje,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney>&nbsp;</TD>
			</TR>

	</TBODY>
</TABLE>
<table width="100%">
		<tr>
			<td width="70%" height="30">注：点击单据号可以查看原始单据。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<br>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>