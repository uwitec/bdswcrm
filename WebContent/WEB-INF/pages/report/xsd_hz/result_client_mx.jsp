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

List list = xsdHzService.getClientMxList(start_date, end_date,dj_id, client_name);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户销售执行汇总--单据列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function openWin(url){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'原始单据',fea);	
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:30;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户销售执行汇总--单据列表</B></font></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>单据号</TD>			
			<TD class=ReportHead>客户名称</TD>
			<TD class=ReportHead>时间</TD>
			<TD class=ReportHead>经手人</TD>	
			<TD class=ReportHead>状态</TD>
			
			<TD class=ReportHead>商品编号</TD>
			<TD class=ReportHead>商品名称</TD>
			<TD class=ReportHead>规格</TD>
			<TD class=ReportHead>数量</TD>
			
			<TD class=ReportHead>订单金额</TD>
			<TD class=ReportHead>成交金额</TD>
		</TR>
	</THEAD>
	<TBODY>

<%
if(list != null && list.size()>0){
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		
		String id = StringUtils.nullToStr(map.get("dj_id"));
		String name = StringUtils.nullToStr(map.get("client_name"));
		String state = StringUtils.nullToStr(map.get("state"));
		String creatdate = StringUtils.nullToStr(map.get("creatdate"));
		String jsr = StringUtils.nullToStr(map.get("jsr"));
		String url = StringUtils.nullToStr(map.get("url"));
		
		double xsdje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
		double sjcjje = map.get("sjcjje")==null?0:((Double)map.get("sjcjje")).doubleValue();
		
%>
		<TR>
			<TD class=ReportItemXh><a href="#" onclick="openWin('<%=url + id %>');"><%=id %></a></TD>					
			<TD class=ReportItem><%=StaticParamDo.getClientNameById(name) %></TD>
			<TD class=ReportItem><%=creatdate %></TD>
			<TD class=ReportItem><%=StaticParamDo.getRealNameById(jsr) %></TD>
			<TD class=ReportItemXh><%=state %></TD>	
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(xsdje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(sjcjje,2) %>&nbsp;</TD>
		</TR>
	
<%
		List mxList = xsdHzService.getDjmxList(id);

		if(mxList != null && mxList.size()>0){
			for(int k=0;k<mxList.size();k++){
				Map mxMap = (Map)mxList.get(k);
				
				double je = mxMap.get("je")==null?0:((Double)mxMap.get("je")).doubleValue();
%>
				<TR>
					<TD class=ReportItem>&nbsp;</TD>
					<TD class=ReportItem>&nbsp;</TD>			
					<TD class=ReportItem>&nbsp;</TD>
					<TD class=ReportItem>&nbsp;</TD>
					<TD class=ReportItem>&nbsp;</TD>
					
					<TD class=ReportItem><%=StringUtils.nullToStr(mxMap.get("product_id")) %>&nbsp;</TD>
					<TD class=ReportItem><%=StringUtils.nullToStr(mxMap.get("product_name")) %>&nbsp;</TD>
					<TD class=ReportItem><%=StringUtils.nullToStr(mxMap.get("product_xh")) %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=StringUtils.nullToStr(mxMap.get("nums")) %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>			
					<TD class=ReportItemMoney>&nbsp;</TD>
				</TR>
<%				
			}
		}
	}
}
%>
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
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>