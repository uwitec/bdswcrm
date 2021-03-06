<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List productList = (List)VS.findValue("productList");


String dj = StringUtils.nullToStr(request.getParameter("dj"));
String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
String store_id = StringUtils.nullToStr(request.getParameter("store_id"));
String flag = StringUtils.nullToStr(request.getParameter("flag"));
String state = StringUtils.nullToStr(request.getParameter("state"));

String conStr = "";

if(!store_id.equals("")){
	conStr += "&nbsp;&nbsp;<b>库房：</b>" + StaticParamDo.getStoreNameById(store_id);
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存金额汇总——统计结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script language='JavaScript' src="js/date.js"></script>

<script language="javascript" src="print/LodopFuncs.js"></script>
<script language="javascript" src="print/print.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
<script type="text/javascript">

   function openMx(product_kind,product_name,store_id,state,flag){
		location.href="getKcJeMxResults.html?product_kind=" + product_kind + "&product_name=" + product_name + "&store_id=" + store_id + "&state=" + state + "&flag=" + flag ;
	}	
	
	function pageRefresh(){
		document.refreshFormMain.submit();
	}

</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<form name="refreshFormMain" action="getKcJeResult.html" method="post">
<input type="hidden" name="product_name" value="<%=product_name %>">
<input type="hidden" name="store_id" value="<%=store_id %>">
<input type="hidden" name="state" value="<%=state %>">
<input type="hidden" name="flag" value="<%=flag %>">
<input type="hidden" name="dj" value="<%=dj %>">
</form>
<div id="printDIV">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>库存金额汇总</B></font><br><%=conStr %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>			
			<TD class=ReportHead>商品类别名称</TD>			
			<TD class=ReportHead>库存数量</TD>			
			<TD class=ReportHead>库存金额</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
int hj_kcNums = 0;
double hj_je = 0;
if(productList != null && productList.size()>0){
	for(int i=0;i<productList.size();i++){
		Map map = (Map)productList.get(i);
		
		String id = StringUtils.nullToStr(map.get("id"));
		String name = StringUtils.nullToStr(map.get("name"));
		
		String strBlank = "";
		for(int k=0;k<id.length()/2;k++){
			strBlank += "　　　";
		}		
		double je = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		String num = StringUtils.nullToStr(map.get("nums"));
		if(num.equals("")){
			num = "0";
		}
		int kcNums = Integer.parseInt(num);
		
		if(id.length() == 2){
			hj_kcNums += kcNums;
			hj_je += je;			
		}
			
		String stl = "BACKGROUND-COLOR: #FFFFFF";
		if(id.length() == 2){
			stl = "BACKGROUND-COLOR: #E8E8E8";
		}else if(id.length() == 4){
			stl = "BACKGROUND-COLOR: #FBFBFB";
		}
		
		if(kcNums != 0 || je != 0){
%>
		<TR style="<%=stl %>">
			<TD class=ReportItem><%=strBlank %><a href="javascript:openMx('<%=id %>','<%=product_name %>','<%=store_id %>','<%=state %>','<%=flag %>');"><%=name %></a>&nbsp;</TD>
			<TD class=ReportItemMoney><%=kcNums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>				
		</TR>
	
<%
		}
	}
}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合计</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_kcNums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
		</TR>
		
	</TBODY>
</TABLE>

<table width="99%">
		<tr>
			<td colspan="2" align="right" height="30" width="100%">生成报表时间：<%=DateComFunc.getCurTime() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
</div>
<center>
	<input type="button" name="button_print" value=" 打印预览" onclick="printPre('2','A4','printDIV');"> &nbsp;&nbsp;
	<input type="button" name="button_print" value=" 打 印 " onclick="printReport('库存金额汇总统计表','2','A4','printDIV');"> &nbsp;&nbsp;
	<input type="button" name="button_refresh" value=" 刷 新 " onclick="pageRefresh();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showKcJeCondition.html';"> 
</center>

</div>
</body>
</html>