<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List resultList = (List)VS.findValue("reustls");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));

String srlx = StringUtils.nullToStr(request.getParameter("type"));        //收入类型

String con = "日期：" + start_date + "至" + end_date;
if(!srlx.equals("")){
	con += "&nbsp;&nbsp;<b>收入类型：</b>" + srlx;
}

%>

<html>
<head>
<title>其他收入统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function pageRefresh(){
		document.refreshForm.submit();
	}
</script>
</head>
<body align="center" >
<form name="refreshForm" action="getQtsrtjResult.html" method="post">
<input type="hidden" name="start_date" value="<%=start_date %>">
<input type="hidden" name="end_date" value="<%=end_date %>">
</form>
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>其他收入统计</B></font><br><%=con %></TD>
		    <TD align="center">
		    	<input type="button" name="btnRes" value="刷新" onclick="pageRefresh();">
		    </TD>		    
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>类型名称</TD>
			<TD class=ReportHead>收入金额</TD>		
		</TR>
	</THEAD>
	<TBODY>
<%
double hj_je = 0;   //合计金额

if(resultList != null && resultList.size()>0){
	for(int i=0;i<resultList.size();i++){
		Map map = (Map)resultList.get(i);
		
		String  type= StringUtils.nullToStr(map.get("type"));
		String xh=StringUtils.nullToStr(map.get("xm_id"));  //收入类型
		String strBlank = "";
		for(int k=0;k<type.length()/2;k++){
			strBlank += "　　　";
		}
		
		double je = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		
		if(type.length() == 2){
			hj_je += je;	
		}
		
		String stl = "BACKGROUND-COLOR: #FFFFFF";
		if(type.length() == 2){
			stl = "BACKGROUND-COLOR: #E8E8E8";
		}else if(type.length() == 4){
			stl = "BACKGROUND-COLOR: #FBFBFB";
		}
		
		if(je != 0){
%>
		<TR style="<%=stl %>">
			<TD class=ReportItem><a href="getQtsrtjResultMx.html?start_date=<%=start_date %>&end_date=<%=end_date %>&type=<%=xh %>"><%=type %></a>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		</TR>
	
<%
		}
	}
}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合计</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
		</TR>
		
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：类别名称可查看明细信息。</td>
			<td align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showQtsrtjCondition.html';"> 
</center>
</body>
</html>