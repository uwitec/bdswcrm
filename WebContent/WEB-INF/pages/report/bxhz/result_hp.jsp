<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

BxhzService bxhzService = (BxhzService)VS.findValue("bxhzService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String gcs=StringUtils.nullToStr(request.getParameter("gcs"));
String lxr=StringUtils.nullToStr(request.getParameter("lxr"));
String clientId = StringUtils.nullToStr(request.getParameter("clientName"));

String product_name=StringUtils.nullToStr(request.getParameter("product_name"));
String product_xh=StringUtils.nullToStr(request.getParameter("product_xh"));
 
String con = "";
con = "日期：" + start_date + "至" + end_date;
if(!clientId.equals("")){
	con += "&nbsp; 客户名称：" + StaticParamDo.getClientNameById(clientId);
}
if(!lxr.equals("")){
	con += "&nbsp; 联系人：" + lxr;
}
if(!product_name.equals("")){
	con += "&nbsp; 商品名称：" + product_name;
}
if(!gcs.equals(""))
{
   con += "&nbsp; 工程师：" + StaticParamDo.getRealNameById(gcs);   
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>货品报修汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
    function openWin(id){
		var destination = "viewBxd.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>货品报修汇总</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>接修日期</TD>
			<TD class=ReportHead>结单日期</TD>
			<TD class=ReportHead>单据</TD>
			<TD class=ReportHead>客户</TD>
			<TD class=ReportHead>联系人</TD>
			<TD class=ReportHead>商品名称</TD>
			 
			<TD class=ReportHead>工程师</TD>
			<TD class=ReportHead>接修人</TD>			
		</TR>
	</THEAD>
	<TBODY>
<%
List list = bxhzService.getHpbxList( start_date,  end_date, clientId, lxr, product_name, gcs);
if(list != null && list.size()>0)
{

	for(int i=0;i<list.size();i++)
	{
		Map map = (Map)list.get(i);	
%>
	<TR>
			<TD class=ReportItem><%= StringUtils.nullToStr(map.get("jxdate")) %>&nbsp;</TD>
			<TD class=ReportItem><%= StringUtils.nullToStr(map.get("jddate")) %>&nbsp;</TD>
			<TD class=ReportItem><a href="javascript:void(0);" onclick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');" title="点击查看原始单据"><%=StringUtils.nullToStr(map.get("id"))  %></a>&nbsp;</TD>		
			<TD class=ReportItem><% if(StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))).trim().equals("")){out.print("零售客户");}else {out.print(StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))));} %>&nbsp;</TD>
			 <TD class=ReportItem><%=StringUtils.nullToStr(map.get("lxr")) %>&nbsp;</TD>
			  <TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_name"))%>&nbsp;</TD>
			 
			  <TD class=ReportItem><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("gcs"))) %>&nbsp;</TD>
			   <TD class=ReportItem><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("jxr"))) %>&nbsp;</TD>
		</TR>	
<%
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
	<input type="button" name="button_print" value=" 打 印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>


	