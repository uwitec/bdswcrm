<%@ page language="java" errorPage="/error.jsp" pageEncoding="GBK" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XstjClientService xstjClientService = (XstjClientService)VS.findValue("xstjClientService");
List resultList = (List)VS.findValue("resultList");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));   //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));       //截止时间
String xsry_id = StringUtils.nullToStr(request.getParameter("xsry_id"));         //销售人员编号
String dj_id = StringUtils.nullToStr(request.getParameter("dj_id"));             //单据编号
String client_name = StringUtils.nullToStr(request.getParameter("client_name")); //客户编号
String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));         //商品类别
String product_name = StringUtils.nullToStr(request.getParameter("product_name"));             //商品名称

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>零售汇总</title>
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
	function openWin(url,winTitle){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,winTitle,fea);	
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>零售汇总</B></font><br>日期：<%=start_date %> 至 <%=end_date %>  </TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>日期</TD>
			<TD class=ReportHead>销售人员</TD>
			<TD class=ReportHead>商品名称</TD>
			<TD class=ReportHead>型号</TD>
			<TD class=ReportHead>单价</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>金额</TD>	
		</TR>
	</THEAD>
	<TBODY>
<%		
//取销售单列表数据


double hjje = 0;
if(resultList != null && resultList.size()>0){
	for(int k=0;k<resultList.size();k++){
		Map map = (Map)resultList.get(k);
		
		
		
		String creatdate = StringUtils.nullToStr(map.get("creatdate"));
		String lsd_id = StringUtils.nullToStr(map.get("id"));
		String xsry_name = StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("xsry")));

		double lsdje = map.get("lsdje")==null?0:((Double)map.get("lsdje")).doubleValue();
		
		hjje += lsdje;	
%>
		<TR>
			<TD class=ReportItem style="font-weight:bold">
				<a style="cursor:hand" onclick="openWin('viewLsd.html?id=<%=lsd_id %>','零售单');" title="点击查看原始单据"><%=lsd_id %></a>
			</TD>
			<TD class=ReportItem style="font-weight:bold"><%=creatdate %></TD>
			<TD class=ReportItem style="font-weight:bold"><%=xsry_name %></TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(lsdje,2) %>&nbsp;</TD>
		</TR>
		
<%

		List mxList = xstjClientService.getLsdMxList(lsd_id);
		

		if(mxList != null && mxList.size()>0){
			for(int l=0;l<mxList.size();l++){
				Map mxMap = (Map)mxList.get(l);	
				
				String product_name_mx = StringUtils.nullToStr(mxMap.get("product_name"));
				String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
				
				double price = mxMap.get("price")==null?0:((Double)mxMap.get("price")).doubleValue();
				
				int nums = new Integer(StringUtils.nullToStr(mxMap.get("nums"))).intValue();
				double xj = mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue();	
				
%>
				<TR>
					<TD class=ReportItem>&nbsp;</TD>
					<TD class=ReportItem>&nbsp;</TD>
					<TD class=ReportItem>&nbsp;</TD>					
					<TD class=ReportItem><%=product_name_mx %>&nbsp;</TD>
					<TD class=ReportItem><%=product_xh %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
				</TR>
<%				
			}
		}
	}
}
%>
	
		<TR>
			<TD class=ReportItem style="font-weight:bold">合计金额&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>					
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;&nbsp;</TD>
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
	<input type="button" name="button_print" value=" 打印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>