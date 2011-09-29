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
String dj_id = StringUtils.nullToStr(request.getParameter("dj_id"));

String con = "";
con = "日期：" + start_date + "至" + end_date;
if(!client_name.equals("")){
	con += "&nbsp; 客户名称：" + client_name;
}
if(!dj_id.equals("")){
	con += "&nbsp; 业务单据号：" + dj_id;
}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户销售执行汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(dj_id,start_date,end_date,client_name){
		location.href = "getClientxsMxResult.html?dj_id=" + dj_id + "&start_date=" + start_date + "&end_date=" + end_date + "&client_name=" + client_name;
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户销售执行汇总</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>客户编号</TD>
			<TD class=ReportHead>客户名称</TD>
			<TD class=ReportHead>联系人</TD>
			<TD class=ReportHead>联系电话</TD>
			<TD class=ReportHead>未出库金额</TD>
			<TD class=ReportHead>已出库金额</TD>	
		</TR>
	</THEAD>
	<TBODY>
<%
double hj_je = 0;
double hjw_je = 0;
String client_idNext="" ;
double jeNext=0;
List list = xsdHzService.getClientXsList(start_date, end_date, dj_id, client_name);
if(list != null && list.size()>0){
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		String client_id = StringUtils.nullToStr(map.get("client_id"));
		String state = StringUtils.nullToStr(map.get("state"));
		String name = StringUtils.nullToStr(map.get("client_name"));
		String lxr = StringUtils.nullToStr(map.get("lxr"));
		String lxdh = StringUtils.nullToStr(map.get("lxdh"));
		double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();

       
		  if((i+1)!=list.size())
		  {
		    Map mapNext = (Map)list.get(i+1);
		    client_idNext = StringUtils.nullToStr(mapNext.get("client_id"));
		    jeNext = mapNext.get("je")==null?0:((Double)mapNext.get("je")).doubleValue();
		  }	 
		  
		    
%>
		<TR>
			<TD class=ReportItemXh><%=client_id %>&nbsp;</TD>
			<TD class=ReportItem><a href="#" onclick="openWin('<%=dj_id %>','<%=start_date %>','<%=end_date %>','<%=client_id %>');"><%=name %></a>&nbsp;</TD>			
			<TD class=ReportItem><%=lxr %>&nbsp;</TD>
			<TD class=ReportItem><%=lxdh %>&nbsp;</TD>
		<%
		 if((state.equals("未出库"))){
		 %>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		<% 
		hjw_je+=je;		
		}
		else if((state.equals("已出库"))){
		%>
		    <TD class=ReportItemMoney>&nbsp;</TD>
		    <TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		<% 
		hj_je+=je;		
		}
		
		if(state.equals("未出库"))	{
		if(client_id.equals(client_idNext)){
		%>
			<TD class=ReportItemMoney><%=JMath.round(jeNext,2) %>&nbsp;</TD>
		<% 
		  hj_je+=jeNext;
		  i=i+1;		 
		}else {
		%>
		  <TD class=ReportItemMoney>&nbsp;</TD>
		<%
		}
		}
%>
       </TR>   
<%
     client_idNext ="";
	 jeNext = 0;
	}
}
%>
		<TR>
			<TD class=ReportItem style="font-weight:bold">合计：</TD>
			<TD class=ReportItem>&nbsp;</TD>			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hjw_je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击客户名称可查看原始单据列表。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>