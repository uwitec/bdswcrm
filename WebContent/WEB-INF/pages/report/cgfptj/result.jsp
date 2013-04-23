<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

CgfptjService cgfptjService = (CgfptjService)VS.findValue("cgfptjService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("clientId"));
String con = "";
con = "日期：" + start_date + "至" + end_date;
if(!client_name.equals("")){
	con += "&nbsp;&nbsp; 客户名称：" + StaticParamDo.getClientNameById(client_name);
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>采购发票统计</title>
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
	function openWin(url)
	{
		var fea ='width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>采购发票统计</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
		    <TD class=ReportHead rowspan="2">单位编码</TD>
			<TD class=ReportHead rowspan="2">单位名称</TD>
			<TD class=ReportHead colspan="2">未入库</TD>
			<TD class=ReportHead colspan="2">已入库</TD>	
		</TR>
		<TR>
			<TD class=ReportHead>单据数</TD>
			<TD class=ReportHead>金额</TD>
			<TD class=ReportHead>单据数</TD>
			<TD class=ReportHead>金额</TD>
		</TR>		
	</THEAD>
	<TBODY>
<%
double hj_rk = 0; //合计入库金额
double hj_wrk = 0; //合计未入库金额

int count_rk = 0; //入库数
int count_wrk = 0; //未入库数

List list = cgfptjService.getCgfpHzList(start_date,end_date,client_name);
String gysbh="";
String gysbhNext="";
double cgmoneyNext=0;
String cgnumsNext="0";
if(list != null && list.size()>0)
{
	for(int i=0;i<list.size();i++)
	{
		Map map = (Map)list.get(i);
		
		gysbh = StringUtils.nullToStr(map.get("gysbh"));
		String gysmc = StringUtils.nullToStr(map.get("gysmc"));
		String state = StringUtils.nullToStr(map.get("state"));
		String cgnums=StringUtils.nullToStr(map.get("cgnums"));
				
		double cgmoney = map.get("cgmoney")==null?0:((Double)map.get("cgmoney")).doubleValue();	
		if((i+1)!=list.size())
		{
		Map mapNext = (Map)list.get(i+1);
		gysbhNext = StringUtils.nullToStr(mapNext.get("gysbh"));	
		cgnumsNext=StringUtils.nullToStr(mapNext.get("cgnums"));
				
		cgmoneyNext = mapNext.get("cgmoney")==null?0:((Double)mapNext.get("cgmoney")).doubleValue();		
		}	
%>
		<TR>
			<TD class=ReportItemXh><%=gysbh %></TD>
			<TD class=ReportItem>
			<a href="getCgfpTjMxResult.html?start_date=<%=start_date %>&end_date=<%=end_date %>&client_name=<%=gysbh %>"><%=gysmc %></a>&nbsp;
			</TD>		
			<%			
		if(state.equals("未入库") )
		{
		%>
		    <TD class=ReportItemMoney><%=cgnums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(cgmoney,2) %>&nbsp;</TD>			
		<%	
		hj_wrk+=cgmoney;		
		count_wrk+=new Integer(cgnums).intValue();
		}
		else if((state.equals("已入库")))
		{
		%>			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>	
			<TD class=ReportItemMoney><%=cgnums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(cgmoney,2) %>&nbsp;</TD>
		<% 
		hj_rk+=cgmoney;		
		count_rk+=new Integer(cgnums).intValue();
		}
		if((state.equals("未入库") ))
		{
		if(gysbh.equals(gysbhNext)) 
		{		
		%>
			<TD class=ReportItemMoney><%=cgnumsNext %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(cgmoneyNext,2) %>&nbsp;</TD>		
		<%
		hj_rk+=cgmoneyNext;		
		count_rk+=new Integer(cgnumsNext).intValue();
		i=i+1;	
		}
		else
		{
		%>
		  <TD class=ReportItem>&nbsp;</TD>
		  <TD class=ReportItemMoney>&nbsp;</TD>	
		<%
		}
		}
		%>
		</TR>
		<%	
	  }
	}
%>	
       <tr>
			<TD class=ReportItem style="font-weight:bold">合计：</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=count_wrk %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_wrk,2) %>&nbsp;</TD>
			
			<TD class=ReportItemMoney style="font-weight:bold"><%=count_rk %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_rk,2) %>&nbsp;</TD>
		</tr>						
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击单位名称可查看原始单据列表。</td>
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