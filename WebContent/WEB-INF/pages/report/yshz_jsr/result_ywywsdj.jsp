<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>
<%
  OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

  List wsdjList = (List)VS.findValue("wsdjList");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>业务员未收单据汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url){
		var fea ='width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
</script>
</head>
<body align="center">
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>业务员未收单据汇总</B></font><br></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>日期</TD>
			<TD class=ReportHead>到期日期</TD>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>客户名称</TD>			
			<TD class=ReportHead>销售员</TD>
			<TD class=ReportHead>发生金额</TD>
			<TD class=ReportHead>应收金额</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%
if(wsdjList != null && wsdjList.size()>0){
	
	double hj_je = 0;
	double hj_xsdje = 0;
	
	for(int i=0;i<wsdjList.size();i++){
		Map map = (Map)wsdjList.get(i);
		
		double xsdje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
		double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();
		
		hj_je += je;
		hj_xsdje += xsdje;
%>	
		<TR>
			<TD class=ReportItemXh><%=StringUtils.nullToStr(map.get("creatdate")) %>&nbsp;</TD>
			<TD class=ReportItemXh><%=StringUtils.nullToStr(map.get("ysrq")) %>&nbsp;</TD>
			<TD class=ReportItem><a href="javascript:openWin('viewXsd.html?id=<%=StringUtils.nullToStr(map.get("id")) %>');"><%=StringUtils.nullToStr(map.get("id")) %></a>&nbsp;</TD>
			<TD class=ReportItem><a href="javascript:openWin('queryClients_wsdj.html?client_name=<%=StringUtils.nullToStr(map.get("client_name")) %>');"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))) %>&nbsp;</TD>
			<TD class=ReportItem><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("fzr"))) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(xsdje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		</TR>
<%
	}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合 计</TD>
			<TD class=ReportItemXh>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_xsdje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
		</TR>
<%
}
%>
	</TBODY>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>