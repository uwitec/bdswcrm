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
String productKind = StringUtils.nullToStr(request.getParameter("productKind"));
String kind_name = StringUtils.nullToStr(request.getParameter("kind_name"));
String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
String product_xh = StringUtils.nullToStr(request.getParameter("product_xh"));
String con = "";
con = "日期：" + start_date + "至" + end_date;
if(!kind_name.equals("")){
	con += "&nbsp; 商品类别：" + kind_name;
}
if(!product_name.equals("")){
	con += "&nbsp; 商品名称：" + product_name;
}
if(!product_xh.equals("")){
	con += "&nbsp; 商品规格：" + product_xh;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>货品销售执行汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>货品销售执行汇总</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
		    <TD class=ReportHead rowspan="2">商品编码</TD>
			<TD class=ReportHead rowspan="2">商品名称</TD>
			<TD class=ReportHead rowspan="2">商品规格</TD>
			<TD class=ReportHead colspan="2">未出库</TD>
			<TD class=ReportHead colspan="2">已出库</TD>	
		</TR>
		<TR>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>金额</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>金额</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
int hj_nums = 0;
double hj_je = 0;
int hjw_nums = 0;
double hjw_je = 0;
String productNext_id="" ;
double jeNext=0;
String strNumsNext="";
List list = xsdHzService.getHpxsList(productKind, start_date, end_date, product_name, product_xh);
if(list != null && list.size()>0){
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		String product_id = StringUtils.nullToStr(map.get("product_id"));
		double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();
		String strNums = StringUtils.nullToStr(map.get("nums"));
		String state = StringUtils.nullToStr(map.get("state"));
		if((i+1)!=list.size())
		{
		  Map mapNext = (Map)list.get(i+1);
		  productNext_id = StringUtils.nullToStr(mapNext.get("product_id"));
		  jeNext = mapNext.get("je")==null?0:((Double)mapNext.get("je")).doubleValue();
		  strNumsNext = StringUtils.nullToStr(mapNext.get("nums"));
		}
		int nums = 0;
		if(!strNums.equals("")){
			nums = new Integer(strNums).intValue();
		}		
%>
		<TR>
			<TD class=ReportItemXh><%=product_id %>&nbsp;</TD>
			<TD class=ReportItem><a href="getHpxsMxCondition.html?product_id=<%=product_id %>&start_date=<%=start_date %>&end_date=<%=end_date %>"><%=StringUtils.nullToStr(map.get("product_name")) %></a>&nbsp;</TD>			
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_xh")) %>&nbsp;</TD>
		<% 
		    if((state.equals("未出库"))){
		%>	
		    <TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>			
		<% 
		hjw_je+=je;		
		hjw_nums+=nums;
		}
		else if((state.equals("已出库"))){
		%>
		    <TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>	
			<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		<% 
		hj_je+=je;		
		hj_nums+=nums;
		}
		
		if((state.equals("未出库") ))	{
		if(product_id.equals(productNext_id)){
		%>
		    <TD class=ReportItemMoney><%=new Integer(strNumsNext).intValue() %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(jeNext,2) %>&nbsp;</TD>
		<% 
		  hj_je+=jeNext;		
		  hj_nums+=new Integer(strNumsNext).intValue();
		  i=i+1;		 
		}else {
		%>
		  <TD class=ReportItemMoney>&nbsp;</TD>
		  <TD class=ReportItemMoney>&nbsp;</TD>
		<%
		}
		}
%>
       </TR>   
<%
      }
      }
 %>
		<TR>
			<TD class=ReportItem style="font-weight:bold">合计：</TD>
			<TD class=ReportItem>&nbsp;</TD>			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hjw_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hjw_je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
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
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>