<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List resultList = (List)VS.findValue("reustls");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String dj = StringUtils.nullToStr(request.getParameter("dj"));
String fy_type = StringUtils.nullToStr(request.getParameter("fy_type"));        //费用类型

String con = "日期：" + start_date + "至" + end_date;
if(!fy_type.equals("")){
	con += "&nbsp;&nbsp;<b>费用类型：</b>" + StaticParamDo.getFyTypeNameById(fy_type);
}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>部门费用汇总表</title>
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
	function pageRefresh(){
		document.refreshForm.submit();
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<form name="refreshForm" action="getDeptFytjResult.html" method="post">
<input type="hidden" name="start_date" value="<%=start_date %>">
<input type="hidden" name="end_date" value="<%=end_date %>">
<input type="hidden" name="fy_type" value="<%=fy_type %>">
<input type="hidden" name="dj" value="<%=dj %>">
</form>
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>部门费用汇总表</B></font><br><%=con %></TD>
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
			<TD class=ReportHead>部门名称</TD>
			<TD class=ReportHead>费用金额</TD>		
		</TR>
	</THEAD>
	<TBODY>
<%
double hj_je = 0;   //合计金额

if(resultList != null && resultList.size()>0){
	for(int i=0;i<resultList.size();i++){
		Map map = (Map)resultList.get(i);
		
		String id = StringUtils.nullToStr(map.get("dept_id"));
		String name = StringUtils.nullToStr(map.get("dept_name"));
		
		String strBlank = "";
		for(int k=0;k<id.length()/2;k++){
			strBlank += "　　　";
		}
		
		double je = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		
		if(id.length() == 2){
			hj_je += je;	
		}
		
		String stl = "BACKGROUND-COLOR: #FFFFFF";
		if(id.length() == 2){
			stl = "BACKGROUND-COLOR: #E8E8E8";
		}else if(id.length() == 4){
			stl = "BACKGROUND-COLOR: #FBFBFB";
		}
		
		if(je != 0){
%>
		<TR style="<%=stl %>">
			<TD class=ReportItem><%=strBlank %><a href="getFytjResultMx.html?start_date=<%=start_date %>&end_date=<%=end_date %>&fy_type=<%=fy_type %>&dept=<%=id %>"><%=name %></a>&nbsp;</TD>
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
	<input type="button" name="button_print" value=" 打 印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showDeptFytjCondition.html';"> 
</center>
</div>
</body>
</html>