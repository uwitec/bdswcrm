<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

YsmxReportService ysmxReportService = (YsmxReportService)VS.findValue("ysmxReportService");

String client_name = StringUtils.nullToStr(request.getParameter("client_name"));  //客户编号
String start_date = StringUtils.nullToStr(request.getParameter("start_date"));   //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));       //结束时间

%>

<html>
<head>
<title>应收单据明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url,winTitle){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,winTitle,fea);	
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px;line-height:30px"><B>应收单据明细</B></font><br>
		    	<font style="FONT-SIZE: 12px;line-height:24px">客户编号：<%=client_name %></font>&nbsp;&nbsp;
		    	<font style="FONT-SIZE: 12px;line-height:24px">客户名称：<%=StaticParamDo.getClientNameById(client_name) %></font>&nbsp;&nbsp;
		    	<font style="FONT-SIZE: 12px;line-height:24px">时间：<%=start_date %> 至 <%=end_date %></font>
		    </TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>日期</TD>
			<TD class=ReportHead>到期日期</TD>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>客户名称</TD>	
			<TD class=ReportHead>联系人</TD>	
			<TD class=ReportHead>电话</TD>	
			<TD class=ReportHead>销售员</TD>
			<TD class=ReportHead>发生金额</TD>
			<TD class=ReportHead>应收金额</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%
double hj_je = 0;
double hj_xsdje = 0;
List results = ysmxReportService.getWsdjList(start_date,end_date,"","",client_name);
if(results != null && results.size()>0){
	
	for(int i=0;i<results.size();i++){
		Map map = (Map)results.get(i);
		
		double xsdje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
		double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();
		
		hj_je += je;
		hj_xsdje += xsdje;
%>	
		<TR>
			<TD class=ReportItemXh><%=StringUtils.nullToStr(map.get("creatdate")) %>&nbsp;</TD>
			<TD class=ReportItemXh><%=StringUtils.nullToStr(map.get("ysrq")) %>&nbsp;</TD>
			<TD class=ReportItem><a href="javascript:openWin('viewXsd.html?id=<%=StringUtils.nullToStr(map.get("id")) %>');"><%=StringUtils.nullToStr(map.get("id")) %></a>&nbsp;</TD>
			<TD class=ReportItem><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("kh_lxr")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("kh_lxdh")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("fzr"))) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(xsdje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		</TR>
<%
	}
}
%>
		
		<TR>
			<TD class=ReportItemXh>合计</TD>		
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(hj_xsdje,2) %>&nbsp;</TD>				
			<TD class=ReportItemMoney><%=JMath.round(hj_je,2) %>&nbsp;</TD>
		</TR>		

	</TBODY>
	
</TABLE>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击产品编号可查看原始单据列表</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返回 " onclick="history.go(-1);"> 
</center>
</body>
</html>