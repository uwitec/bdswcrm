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
//汇总结果
List statResult = (List)VS.findValue("statResult");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户销售汇总</title>
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
	
	function openWinLs(){	    
		//location.href = "getXstjClientResultLsMx.html?start_date=<%=start_date%>&end_date=<%=end_date%>&xsry_id=<%=xsry_id%>&dj_id=<%=dj_id%>&product_kind=<%=product_kind%>&product_name=<%=product_name%>";	
	    
	    document.refreshForm.action = "getXstjClientResultLsMx.html" ;	
        document.refreshForm.submit();
	}
	
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<form name="refreshForm" action="getXstjClientResult.html" method="post">
<input type="hidden" name="client_name" value="<%=client_name %>">
<input type="hidden" name="start_date" value="<%=start_date %>">
<input type="hidden" name="end_date" value="<%=end_date %>">
<input type="hidden" name="dj_id" value="<%=dj_id %>">
<input type="hidden" name="xsry_id" value="<%=xsry_id %>">
<input type="hidden" name="product_kind" value="<%=product_kind %>">
<input type="hidden" name="product_name" value="<%=product_name %>">
</form>
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户销售汇总</B></font><br>日期：<%=start_date %> 至 <%=end_date %> </TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>客户编号</TD>
			<TD class=ReportHead>客户名称</TD>
			<TD class=ReportHead>总金额</TD>	
		</TR>
	</THEAD>
	<TBODY>
<%
double hjje = 0;
if(statResult != null && statResult.size()>0){
	for(int i=0;i<statResult.size();i++){
		Map map = (Map)statResult.get(i);
		
		String id = StringUtils.nullToStr(map.get("client_id"));
		String name = StringUtils.nullToStr(map.get("client_name"));
		
		double zje = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		
		boolean is = false;
		if(isShowZ.equals("否")){
			if(zje != 0){
				is = true;
			}
		}else{
			is = true;
		}
		
		if(is){
			hjje += zje;
%>
	<TR>
		<TD class=ReportItem><%=id %>&nbsp;</TD>
		<TD class=ReportItem><a href="javascript:openWin('<%=id %>');"><%=name %></a>&nbsp;</TD>
		<TD class=ReportItemMoney><%=JMath.round(zje,2) %>&nbsp;</TD>
	</TR>
<%
		}
	}
}

if(client_name.equals("")&& client_type.equals("") && khjl.equals("")){

	double lsdzje = xstjClientService.getLsdZje(start_date, end_date,xsry_id,dj_id,product_kind,product_name);
	hjje += lsdzje;
%>

	<TR>
		<TD class=ReportItem>&nbsp;</TD>		
		<TD class=ReportItem><a href="javascript:openWinLs();">零售单总计</a>&nbsp;</TD>		
		<TD class=ReportItemMoney><%=JMath.round(lsdzje,2) %>&nbsp;</TD>
	</TR>
<%
}
%>

	<TR>
		<TD class=ReportItem style="font-weight:bold">合计：</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hjje,2) %>&nbsp;</TD>
	</TR>
	</TBODY>
</TABLE>

<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击商品编号可查看原始单据列表。</td>
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