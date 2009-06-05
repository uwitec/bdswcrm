<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XstjXsryService xstjXsryService = (XstjXsryService)VS.findValue("xstjXsryService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));  //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));      //结束时间
String dept_id = StringUtils.nullToStr(request.getParameter("dept_id"));        //部门
String user_id = StringUtils.nullToStr(request.getParameter("user_id"));        //业务员编号

//业务员列表
List userList = (List)VS.findValue("userList");


String strCon = "";

strCon = "日期：" + start_date + "至" + end_date;

if(!dept_id.equals("")){
	strCon += "&nbsp;&nbsp;部门：" + StaticParamDo.getDeptNameById(dept_id);
}

if(!user_id.equals("")){
	strCon += "&nbsp;&nbsp;业务员：" + StaticParamDo.getRealNameById(user_id);
}
%>

<html>
<head>
<title>业务员销售毛利汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function subForm(vl){
		document.reportForm.xsry_id.value = vl;		
		document.reportForm.submit();
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>业务员考核毛利汇总</B></font><br><%=strCon %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>部门</TD>
			<TD class=ReportHead>业务员姓名</TD>
			<TD class=ReportHead>销售收入</TD>
			<TD class=ReportHead>销售成本</TD>
			<TD class=ReportHead>毛利</TD>	
			<TD class=ReportHead>毛利率</TD>	
		</TR>
	</THEAD>
	
	<TBODY>
<%
if(userList != null && userList.size()>0){
	
	double hj_xssr = 0;
	double hj_khcb = 0;
	double hj_ml = 0;
	
	for(int i=0;i<userList.size();i++){
		SysUser user = (SysUser)userList.get(i);
		
		String user_name = user.getReal_name();
		String dept_name = StaticParamDo.getDeptNameById(user.getDept());
		
		HashMap<String,Double> map = xstjXsryService.getMlHz(user.getUser_id(),start_date,end_date);
		
		double xssr = map.get("xssr");
		double khcb = map.get("khcb");
		
		double ml = xssr - khcb;
		
		hj_xssr += xssr;
		hj_khcb += khcb;
		hj_ml += ml;
		
		String mll = JMath.percent(ml,xssr);

%>	
		<TR>
			<TD class=ReportItem><%=dept_name %>&nbsp;</TD>
			<TD class=ReportItem><a href="getXsryKhmlMxResult.html?start_date=<%=start_date %>&end_date=<%=end_date %>&user_id=<%=user.getUser_id() %>"><%=user_name %></a>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(xssr,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(khcb,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ml,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=mll %>&nbsp;</TD>
		</TR>
<%
	}
%>
		<TR>
			<TD class=ReportItem style="font-weight:bold">合计</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_xssr,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_khcb,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_ml,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.percent(hj_ml,hj_xssr) %>&nbsp;</TD>
		</TR>
<%
}
%>
	</TBODY>
</TABLE>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击业务员姓名可以查看明细。</td>
			<td align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>