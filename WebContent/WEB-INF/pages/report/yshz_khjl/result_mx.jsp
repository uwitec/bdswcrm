<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

ClientWlDzdService clientWlDzdService = (ClientWlDzdService)VS.findValue("clientWlDzdService");
List dzdList = (List)VS.findValue("dzdList");
Map qcMap = (Map)VS.findValue("qcMap");

String start_date = StringUtils.nullToStr(VS.findValue("start_date"));
String end_date = StringUtils.nullToStr(VS.findValue("end_date"));
String client_name = StringUtils.nullToStr(VS.findValue("client_name"));
String khjl = StringUtils.nullToStr(VS.findValue("khjl"));

String con = "";
con = "日期：" + start_date + "至" + end_date + "&nbsp;&nbsp; 客户经理：" + StaticParamDo.getRealNameById(khjl);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户经理应收对账单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
	function openWin(url){
		var fea ='width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户经理应收对账单</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>客户名称</TD>
			<TD class=ReportHead>日期</TD>
			<TD class=ReportHead>业务类型</TD>
			<TD class=ReportHead>单据编号</TD>				
			<TD class=ReportHead>应收款</TD>
			<TD class=ReportHead>收款</TD>
			<TD class=ReportHead>余额</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
//处理期初
double ysqc = 0;
if(qcMap != null){
	ysqc = qcMap.get(khjl)==null?0:((Double)qcMap.get(khjl)).doubleValue();
}
%>
		<tr>
			<TD class=ReportItemXH><B>期 初</B></TD>
			<TD class=ReportItemXH>&nbsp;</TD>
			<TD class=ReportItemXH>&nbsp;</TD>
			<TD class=ReportItemXH>&nbsp;</TD>				
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ysqc,2) %>&nbsp;</TD>
		</tr>
<%

double hj_ys = 0; //合计应收
double hj_sk = 0; //合计收款

double ys_ye = ysqc; //应收余额

if(dzdList != null && dzdList.size()>0){
	for(int i=0;i<dzdList.size();i++){
		Map map = (Map)dzdList.get(i);
		
		String clientName = StringUtils.nullToStr(map.get("client_name"));
		String creatdate = StringUtils.nullToStr(map.get("creatdate"));
		String xwtype = StringUtils.nullToStr(map.get("xwtype"));
		String dj_id = StringUtils.nullToStr(map.get("dj_id"));  //单据编号
		
		double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();
%>
		<TR>
			<TD class=ReportItem><%=clientName %></TD>	
			<TD class=ReportItemXH><%=creatdate %></TD>			
<%			
		String url = "";
		if(xwtype.equals("销售") || xwtype.equals("销售退货")){
			if(xwtype.equals("销售")){
				url = "viewXsd.html?id=" + dj_id;
			}
			if(xwtype.equals("销售退货")){
				url = "viewThd.html?thd_id=" + dj_id;
			}
			
			hj_ys += je;
			ys_ye += je;
		%>
			<TD class=ReportItemXH><%=xwtype %></TD>
			<TD class=ReportItemXH><a href="javascript:openWin('<%=url %>');"><%=dj_id %></TD>
					
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ys_ye,2) %>&nbsp;</TD>
		<%			
		}else if(xwtype.equals("收款")){
			url = "viewXssk.html?id=" + dj_id;
			hj_sk += je;
			ys_ye -= je;
		%>
			<TD class=ReportItemXH><%=xwtype %></TD>
			<TD class=ReportItemXH><a href="javascript:openWin('<%=url %>');"><%=dj_id %></a></TD>
	
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ys_ye,2) %>&nbsp;</TD>
					
		<%			
		}else if(xwtype.equals("往来调账(应收)")){
			url = "viewPz.html?id=" + dj_id;
			hj_ys += je;
			ys_ye += je;
		%>
			<TD class=ReportItemXH><%=xwtype %></TD>
			<TD class=ReportItemXH><a href="javascript:openWin('<%=url %>');"><%=dj_id %></TD>
					
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ys_ye,2) %>&nbsp;</TD>	
		
		<%
		}
%>
		</TR>
<%
	}
}
%>
		<tr>
			<TD class=ReportItemXH style="font-weight:bold"><B>合 计</B></TD>
			<TD class=ReportItemXH style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemXH style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemXH style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_ys,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_sk,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(ys_ye,2) %>&nbsp;</TD>	
		</tr>		
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
	<input type="button" name="button_print" value=" 打 印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>