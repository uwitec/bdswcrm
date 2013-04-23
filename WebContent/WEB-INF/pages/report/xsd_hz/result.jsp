<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XsdHzService xsdHzService = (XsdHzService)VS.findValue("xsdHzService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
 
String xsry_id = StringUtils.nullToStr(request.getParameter("xsry_id"));
String dj_id = StringUtils.nullToStr(request.getParameter("dj_id"));


//销售单列表
List xsdList =  xsdHzService.getXsdList(start_date,end_date,client_name,dj_id);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售订单汇总</title>
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
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>销售订单汇总</B></font><br>日期：<%=start_date %> 至 <%=end_date %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>日期</TD>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>客户</TD>
			<TD class=ReportHead>销售人员</TD>
			<TD class=ReportHead>订单金额</TD>
			<TD class=ReportHead>成交金额</TD>	
		</TR>
	</THEAD>
	<TBODY>
<%

double hj_je = 0;
double hj_cj_je = 0;

if(xsdList != null && xsdList.size()>0){
	for(int i=0;i<xsdList.size();i++){
		Map xsdMap = (Map)xsdList.get(i);
		
		
		
		String creatdate = StringUtils.nullToStr(xsdMap.get("creatdate"));
		String xsd_id = StringUtils.nullToStr(xsdMap.get("id"));
		String name = StringUtils.nullToStr(xsdMap.get("client_name"));
		String xsry = StaticParamDo.getRealNameById(StringUtils.nullToStr(xsdMap.get("fzr")));
		double xsdje = xsdMap.get("xsdje")==null?0:((Double)xsdMap.get("xsdje")).doubleValue();
		double sjcjje = xsdMap.get("sjcjje")==null?0:((Double)xsdMap.get("sjcjje")).doubleValue();
		
		hj_je += xsdje;
		hj_cj_je += sjcjje;
		
%>
		<TR>
			<TD class=ReportItem><%=creatdate %></TD>
			<TD class=ReportItem>
				<a href="#" onclick="openWin('viewXsd.html?id=<%=xsd_id %>');" title="点击查看原始单据"><%=xsd_id %></a>
			</TD>
			<TD class=ReportItem><%=name %>&nbsp;</TD>			
			<TD class=ReportItem><%=xsry %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(xsdje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(sjcjje,2) %>&nbsp;</TD>
		</TR>
	
<%
	}
}
%>

		<TR>
			<TD class=ReportItem style="font-weight:bold">合计（金额）</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>	
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_cj_je,2) %>&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击单据编号可以查看原始单据。</td>
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