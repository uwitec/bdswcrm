<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XstjXsryService xstjXsryService = (XstjXsryService)VS.findValue("xstjXsryService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));  //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));      //结束时间
String dept_id = StringUtils.nullToStr(request.getParameter("dept_id"));        //部门
String user_id = StringUtils.nullToStr(request.getParameter("user_id"));        //业务员编号

String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));      //商品类别
String product_name = StringUtils.nullToStr(request.getParameter("product_name"));      //商品名称/规格
String kind_name = StringUtils.nullToStr(request.getParameter("kind_name"));            //商品类别名称

List results = xstjXsryService.getYwymlHz(start_date,end_date,dept_id,user_id,product_kind,product_name);


String strCon = "";

strCon = "日期：" + start_date + "至" + end_date;

if(!dept_id.equals("")){
	strCon += "&nbsp;&nbsp;部门：" + StaticParamDo.getDeptNameById(dept_id);
}

if(!user_id.equals("")){
	strCon += "&nbsp;&nbsp;业务员：" + StaticParamDo.getRealNameById(user_id);
}

if(!kind_name.equals("")){
	strCon += "&nbsp; 商品类别：" + kind_name;
}
if(!product_name.equals("")){
	strCon += "&nbsp; 商品名称/规格：" + product_name;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>业务员预估毛利汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
function pageRefresh(){
	document.refreshform.submit();
}
function getMx(xsry){
	document.reportForm.user_id.value = xsry;
	document.reportForm.submit();
}
</script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<form name="refreshform" action="getXsryYgmlhzResult.html" method="post">
<input type="hidden" name="start_date" value="<%=start_date %>">
<input type="hidden" name="end_date" value="<%=end_date %>">
<input type="hidden" name="dept_id" value="<%=dept_id %>">
<input type="hidden" name="user_id" value="<%=user_id %>">
</form>
<form name="reportForm" action="getXsryYgmlMxResult.html" method="post">
	<input type="hidden" name="start_date" value="<%=start_date %>">
	<input type="hidden" name="end_date" value="<%=end_date %>">
	<input type="hidden" name="user_id" value="">
	<input type="hidden" name="product_kind" value="<%=product_kind %>">
	<input type="hidden" name="product_name" value="<%=product_name %>">
	<input type="hidden" name="kind_name" value="<%=kind_name %>">
</form>
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>业务员预估毛利汇总</B></font><br><%=strCon %></TD>
		    <TD align="center"><input type="button" name="btnRes" value="刷新" onclick="pageRefresh();"></TD>	
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>部门</TD>
			<TD class=ReportHead>业务员姓名</TD>
			<TD class=ReportHead>销售金额</TD>
			<TD class=ReportHead>不含税金额</TD>
			<TD class=ReportHead>预估成本</TD>
			<TD class=ReportHead>预估毛利</TD>	
			<TD class=ReportHead>预估毛利率</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%

if(results != null && results.size()>0){
	
	double hj_xsje = 0;
	double hj_bhsje = 0;
	double hj_ygcb = 0;
	double hj_ygml = 0;
	
	for(int i=0;i<results.size();i++){
		Map map = (Map)results.get(i);
		
		double xsje = map.get("xsje") == null?0:((Double)map.get("xsje")).doubleValue();
		double bhsje = map.get("bhsje") == null?0:((Double)map.get("bhsje")).doubleValue();
		double ygcb = map.get("ygcb") == null?0:((Double)map.get("ygcb")).doubleValue();
		double ygml = bhsje - ygcb;
		
		hj_xsje += xsje;
		hj_bhsje += bhsje;
		hj_ygcb += ygcb;
		hj_ygml += ygml;
		
		String mll = JMath.percent(ygml,bhsje);

%>	
		<TR>
			<TD class=ReportItemXH><%=StaticParamDo.getDeptNameById((String)map.get("dept")) %>&nbsp;</TD>
			<TD class=ReportItemXH>
				<a href="javascript:getMx('<%=StringUtils.nullToStr(map.get("xsry")) %>');"><%=StringUtils.nullToStr(map.get("real_name")) %></a>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(xsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(bhsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ygcb,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ygml,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=mll %>&nbsp;</TD>
		</TR>
<%
	}
%>
		<TR>
			<TD class=ReportItemXH style="font-weight:bold">合计</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_xsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_bhsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_ygcb,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_ygml,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.percent(hj_ygml,hj_bhsje) %>&nbsp;</TD>
		</TR>
<%
}
%>
	</TBODY>
</TABLE>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：预估毛利 = 不含税金额 - 预估成本；预估毛利率 = 预估毛利 / 不含税金额 * 100%；点击业务员姓名可以查看明细。</td>
			<td align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>