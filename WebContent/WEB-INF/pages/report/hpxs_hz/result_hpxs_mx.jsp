<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List hpxs_resultList = (List)VS.findValue("hpxs_resultList");

String client_type = StringUtils.nullToStr(request.getParameter("client_type"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>明细信息列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
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
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:30;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>明细信息--单据列表</B></font></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
		    <TD class=ReportHead>日期</TD>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>仓库</TD>
			<TD class=ReportHead>业务类型</TD>
			<TD class=ReportHead>往来单位</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>未税单价</TD>
			<TD class=ReportHead>未税金额</TD>
			<TD class=ReportHead>含税单价</TD>
			<TD class=ReportHead>含税金额</TD>
			<TD class=ReportHead>经手人</TD>	
		</TR>
	</THEAD>
	<TBODY>

<%
//销售单
if(hpxs_resultList != null && hpxs_resultList.size()>0){
	for(int i=0;i<hpxs_resultList.size();i++){
		Map map = (Map)hpxs_resultList.get(i);
		
		
		
		String id = StringUtils.nullToStr(map.get("id"));
		String name = StringUtils.nullToStr(map.get("client_name"));
		String creatdate = StringUtils.nullToStr(map.get("cz_date"));
		String fzr = StringUtils.nullToStr(map.get("xsry"));
		String yw_type = StringUtils.nullToStr(map.get("yw_type"));
		String store_id = StringUtils.nullToStr(map.get("store_id"));
		double je = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		String strNums = StringUtils.nullToStr(map.get("nums"));
		int nums = new Integer(strNums).intValue();
		double bhsje = map.get("bhsje")==null?0:((Double)map.get("bhsje")).doubleValue(); 
		double price = je/nums;
		double bhs_price = bhsje/nums;
		
%>
		<TR>
		    <TD class=ReportItemXh><%=creatdate%>&nbsp;</TD>
		    <% if(yw_type.equals("销售单")) {%>		    
			  <TD class=ReportItemXh><a href="#" onclick="openWin('viewXsd.html?id=<%=id %>');"><%=id %></a>&nbsp;</TD>
			<%} 
			else if(yw_type.equals("零售单")) {%>
			  <TD class=ReportItemXh><a href="#" onclick="openWin('viewLsd.html?id=<%=id %>');"><%=id %></a>&nbsp;</TD>
			<%}
			else if(yw_type.equals("退货单"))  {
			 %>
			  <TD class=ReportItemXh><a href="#" onclick="openWin('viewThd.html?thd_id=<%=id %>');"><%=id %></a>&nbsp;</TD>
			 <%} %>
			<TD class=ReportItemXh><%=StaticParamDo.getStoreNameById(store_id)%>&nbsp;</TD>	
			<TD class=ReportItemXh><%=yw_type %>&nbsp;</TD>		
			<TD class=ReportItem><%=StaticParamDo.getClientNameById(name) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(bhs_price,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(bhsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemXh><%=StaticParamDo.getRealNameById(fzr) %>&nbsp;</TD>
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
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>