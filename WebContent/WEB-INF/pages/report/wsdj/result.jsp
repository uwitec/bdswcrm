<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XsmxReportService xsmxReportService = (XsmxReportService)VS.findValue("xsmxReportService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));  //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));      //结束时间
String dept_id = StringUtils.nullToStr(request.getParameter("dept_id"));        //部门
String xsry_id = StringUtils.nullToStr(request.getParameter("xsry_id"));        //销售人员
String client_name = StringUtils.nullToStr(request.getParameter("client_id"));        //客户编号
String cq_flag = StringUtils.nullToStr(request.getParameter("cq_flag"));        //客户编号

String strCon = "";

strCon = "日期：" + start_date + "至" + end_date;

if(!dept_id.equals("")){
	strCon += "&nbsp;&nbsp;部门：" + StaticParamDo.getDeptNameById(dept_id);
}
%>

<html>
<head>
<title>销售未收单据</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
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
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>销售未收单据</B></font><br><%=strCon %></TD>
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
			<TD class=ReportHead>联系人</TD>	
			<TD class=ReportHead>电话</TD>	
			<TD class=ReportHead>销售员</TD>
			<TD class=ReportHead>发生金额</TD>
			<TD class=ReportHead>应收金额</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%
List results = xsmxReportService.getWsdjList(start_date,end_date,dept_id,xsry_id,client_name,cq_flag);
if(results != null && results.size()>0){
	
	double hj_je = 0;
	double hj_xsdje = 0;
	
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
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合 计</TD>
			<TD class=ReportItemXh>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
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
</TABLE>
<BR>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showWsdjCondition.html';"> 
</center>
</body>
</html>