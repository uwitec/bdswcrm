<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("results");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));  //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));      //结束时间
String dept_id = StringUtils.nullToStr(request.getParameter("dept_id"));        //部门
String product_kind = StringUtils.nullToStr(request.getParameter("product_kind")); //商品类别
String client_name = StringUtils.nullToStr(request.getParameter("clientName"));
String clientId = StringUtils.nullToStr(request.getParameter("clientId"));

String strCon = "";

strCon = "日期：" + start_date + "至" + end_date;

if(!dept_id.equals("")){
	strCon += "&nbsp;&nbsp;部门：" + StaticParamDo.getDeptNameById(dept_id);
}

if(!product_kind.equals("")){
	strCon += "&nbsp;&nbsp;商品类别：" + StaticParamDo.getProductKindNameById(product_kind);
}

if(!clientId.equals("")){
	strCon += "&nbsp; 客户名称：" + clientId;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商品序列号销售汇总</title>
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
	function subForm(vl){
		document.reportForm.xsry_id.value = vl;		
		document.reportForm.submit();
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>商品序列号销售汇总</B></font><br><%=strCon %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead width="4%">序号</TD>
			<TD class=ReportHead width="7%">日期</TD>
			<TD class=ReportHead width="10%">序列号</TD>
			<TD class=ReportHead width="20%">商品名称</TD>
			<TD class=ReportHead width="20%">商品规格</TD>
			<TD class=ReportHead width="13%">客户名称</TD>	
			<TD class=ReportHead width="12%">客户电话</TD>	
			<TD class=ReportHead width="7%">销售员</TD>
			<TD class=ReportHead width="7%">销售价格</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%
if(results != null && results.size()>0){
	
	for(int i=0;i<results.size();i++){
		Map map = (Map)results.get(i);
		
		double xsdj = map.get("xsdj")==null?0:((Double)map.get("xsdj")).doubleValue();
		
%>	
		<TR>
			<TD class=ReportItemXh><%=i+1 %>&nbsp;</TD>
			<TD class=ReportItemXh><%=StringUtils.nullToStr(map.get("fs_date")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("serial_num")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_name")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_xh")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("client_name")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("tel")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("jsr")) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(xsdj,2) %>&nbsp;</TD>
		</TR>
<%
	}
}
%>
	</TBODY>
</TABLE>
<BR>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>