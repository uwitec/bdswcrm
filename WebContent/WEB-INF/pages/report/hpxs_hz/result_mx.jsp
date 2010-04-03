<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List xsdList = (List)VS.findValue("xsdList");
List thdList = (List)VS.findValue("thdList");
List lsdList = (List)VS.findValue("lsdList");

String client_type = StringUtils.nullToStr(request.getParameter("client_type"));
%>

<html>
<head>
<title>明细信息列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'原始单据',fea);	
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:30;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>明细信息--单据列表</B></font></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>业务类型</TD>
			<TD class=ReportHead>客户名称</TD>
			<TD class=ReportHead>时间</TD>
			<TD class=ReportHead>经手人</TD>	
		</TR>
	</THEAD>
	<TBODY>

<%
//销售单
if(xsdList != null && xsdList.size()>0){
	for(int i=0;i<xsdList.size();i++){
		Map map = (Map)xsdList.get(i);
		
		
		
		String id = StringUtils.nullToStr(map.get("id"));
		String name = StringUtils.nullToStr(map.get("client_name"));
		String creatdate = StringUtils.nullToStr(map.get("creatdate"));
		String fzr = StringUtils.nullToStr(map.get("fzr"));
		
%>
		<TR>
			<TD class=ReportItemXh><a href="#" onclick="openWin('viewXsd.html?id=<%=id %>');"><%=id %></a>&nbsp;</TD>
			<TD class=ReportItemXh>销售订单&nbsp;</TD>			
			<TD class=ReportItem><%=StaticParamDo.getClientNameById(name) %>&nbsp;</TD>
			<TD class=ReportItemXh><%=creatdate %>&nbsp;</TD>
			<TD class=ReportItemXh><%=StaticParamDo.getRealNameById(fzr) %>&nbsp;</TD>
		</TR>
	
<%
	}
}
%>

<%
//退货单
if(thdList != null && thdList.size()>0){
	for(int i=0;i<thdList.size();i++){
		Map map = (Map)thdList.get(i);
		
		
		
		String thd_id = StringUtils.nullToStr(map.get("thd_id"));
		String name = StringUtils.nullToStr(map.get("client_name"));
		String th_date = StringUtils.nullToStr(map.get("th_date"));
		String th_fzr = StringUtils.nullToStr(map.get("th_fzr"));
		
%>
		<TR>
			<TD class=ReportItemXh><a href="#" onclick="openWin('viewThd.html?thd_id=<%=thd_id %>');"><%=thd_id %></a>&nbsp;</TD>
			<TD class=ReportItemXh>退货单&nbsp;</TD>			
			<TD class=ReportItem><%=StaticParamDo.getClientNameById(name) %>&nbsp;</TD>
			<TD class=ReportItemXh><%=th_date %>&nbsp;</TD>
			<TD class=ReportItemXh><%=StaticParamDo.getRealNameById(th_fzr) %>&nbsp;</TD>
		</TR>
	
<%
	}
}
%>


<%
if(client_type.equals("")){
	//零售单
	if(lsdList != null && lsdList.size()>0){
		for(int i=0;i<lsdList.size();i++){
			Map map = (Map)lsdList.get(i);
			
			
			
			String id = StringUtils.nullToStr(map.get("id"));
			String name = StringUtils.nullToStr(map.get("client_name"));
			String creatdate = StringUtils.nullToStr(map.get("creatdate"));
			String xsry = StringUtils.nullToStr(map.get("xsry"));
		
%>
		<TR>
			<TD class=ReportItemXh><a href="#" onclick="openWin('viewLsd.html?id=<%=id %>');"><%=id %></a>&nbsp;</TD>
			<TD class=ReportItemXh>零售单&nbsp;</TD>			
			<TD class=ReportItem><%=name %>&nbsp;</TD>
			<TD class=ReportItemXh><%=creatdate %>&nbsp;</TD>
			<TD class=ReportItemXh><%=StaticParamDo.getRealNameById(xsry) %>&nbsp;</TD>
		</TR>
	
<%
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
</body>
</html>