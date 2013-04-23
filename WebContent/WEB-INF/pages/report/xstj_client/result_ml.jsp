<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XstjClientService xstjClientService = (XstjClientService)VS.findValue("xstjClientService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));   //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));       //截止时间
String xsry_id = StringUtils.nullToStr(request.getParameter("xsry_id"));         //销售人员编号
String dj_id = StringUtils.nullToStr(request.getParameter("dj_id"));             //单据编号
String isShowZ = StringUtils.nullToStr(request.getParameter("isShowZ"));         //是否显示销售额为零客户
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
String product_name = StringUtils.nullToStr(request.getParameter("product_name")); 
String khjl = StringUtils.nullToStr(request.getParameter("khjl"));         //客户经理
String client_type = StringUtils.nullToStr(request.getParameter("client_type")); //客户类型

List list = xstjClientService.getXstjClientMlResult(start_date, end_date, client_name, xsry_id, client_type, khjl, dj_id, product_kind, product_name);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户销售毛利汇总</title>
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
	function openWin(client_name){
		location.href = "getXstjClientResultMx.html?start_date=<%=start_date%>&end_date=<%=end_date%>&xsry_id=<%=xsry_id%>&dj_id=<%=dj_id%>&client_type=<%=client_type%>&khjl=<%=khjl%>&product_kind=<%=product_kind%>&product_name=<%=product_name%>&client_name=" + client_name;
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户销售毛利汇总</B></font><br>日期：<%=start_date %> 至 <%=end_date %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>客户编号</TD>
			<TD class=ReportHead>客户名称</TD>
			<TD class=ReportHead>客户经理</TD>
			<TD class=ReportHead>金额</TD>	
			<TD class=ReportHead>不含税金额</TD>
			<TD class=ReportHead>成本</TD>
			<TD class=ReportHead>毛利</TD>
			<TD class=ReportHead>毛利率</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
double hj_hjje = 0;
double hj_cb = 0;
double hj_bhsje = 0;
double hj_ml = 0;
if(list != null && list.size()>0){
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		
		String id = StringUtils.nullToStr(map.get("id"));
		String name = StringUtils.nullToStr(map.get("name"));
		String tkhjl = StringUtils.nullToStr(map.get("khjl"));
		
		double hjje = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		double cb = map.get("cb")==null?0:((Double)map.get("cb")).doubleValue();
		double bhsje = map.get("bhsje")==null?0:((Double)map.get("bhsje")).doubleValue();
		double ml = bhsje - cb;
		String mll = JMath.percent(ml,bhsje);
		
		boolean is = false;
		if(isShowZ.equals("否")){
			if(hjje != 0){
				is = true;
			}
		}else{
			is = true;
		}
		
		if(is){
			hj_hjje += hjje;
			hj_bhsje += bhsje;
			hj_cb += cb;
			hj_ml += ml;
%>
	<TR>
		<TD class=ReportItem><%=id %>&nbsp;</TD>
		<TD class=ReportItem><a href="javascript:openWin('<%=id %>');"><%=name %></a>&nbsp;</TD>
		<TD class=ReportItem><%=tkhjl %>&nbsp;</TD>
		<TD class=ReportItemMoney><%=JMath.round(hjje,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney><%=JMath.round(bhsje,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney><%=JMath.round(cb,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney><%=JMath.round(ml,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney><%=mll %>&nbsp;</TD>
	</TR>
<%
		}
	}
}
%>

	<TR>
		<TD class=ReportItem style="font-weight:bold">合计：</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_hjje,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_bhsje,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_cb,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_ml,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.percent(hj_ml,hj_bhsje) %>&nbsp;</TD>
	</TR>
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：毛利 = 不含税金额 - 成本；毛利率 = 毛利 / 不含税金额 * 100%；点击客户名称可查看明细信息。</td>
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