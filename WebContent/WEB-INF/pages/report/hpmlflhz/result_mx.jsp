<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List resultList = (List)VS.findValue("resultList");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String xsry = StringUtils.nullToStr(request.getParameter("xsry"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));

String con = "";
con = "日期：" + start_date + "至" + end_date;
if(!client_name.equals("")){
	con += "&nbsp; 客户名称：" + StaticParamDo.getClientNameById(client_name);
}
if(!xsry.equals("")){
	con += "&nbsp; 销售人员：" + StaticParamDo.getRealNameById(xsry);
}
if(!product_kind.equals("")){
	con += "&nbsp; 商品类别：" + StaticParamDo.getProductKindNameById(product_kind);
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>货品毛利分类汇总--明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">

<link href="css/report.css" rel="stylesheet" type="text/css" >
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(product_id){
		document.myform.product_id.value = product_id;
		document.myform.submit();
	}
	function pageRefresh(){
		document.refreshForm.submit();
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<form name="myform" action="getHpxsHzMxResult.html" method="post">
<input type="hidden" name="product_id" value="">
<input type="hidden" name="start_date" value="<%=start_date %>">
<input type="hidden" name="end_date" value="<%=end_date %>">
<input type="hidden" name="clientName" value="<%=client_name %>">
<input type="hidden" name="xsry_id" value="<%=xsry %>">
</form>
<form name="refreshForm" action="getHpmlflMxResult.html" method="post">
<input type="hidden" name="product_kind" value="<%=product_kind %>">
<input type="hidden" name="client_name" value="<%=client_name %>">
<input type="hidden" name="start_date" value="<%=start_date %>">
<input type="hidden" name="end_date" value="<%=end_date %>">
<input type="hidden" name="xsry" value="<%=xsry %>">
</form>
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>货品毛利分类汇总--明细</B></font><br><%=con %></TD>
		    <TD align="center">
		    	<input type="button" name="btnRes" value="刷新" onclick="pageRefresh();">
		    </TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>商品编码</TD>
			<TD class=ReportHead>商品名称</TD>
			<TD class=ReportHead>商品规格</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>金额</TD>
			<TD class=ReportHead>不含税金额</TD>
			<TD class=ReportHead>成本</TD>
			<TD class=ReportHead>毛利</TD>		
			<TD class=ReportHead>毛利率</TD>		
		</TR>
	</THEAD>
	<TBODY>
<%
int hj_nums = 0;
double hj_je = 0;
double hj_ml = 0;
double hj_cb = 0;   //合计成本
double hj_bhsje = 0;
if(resultList != null && resultList.size()>0){
	for(int i=0;i<resultList.size();i++){
		Map map = (Map)resultList.get(i);
		
		String product_id = StringUtils.nullToStr(map.get("product_id"));
		String prop = StringUtils.nullToStr(map.get("prop"));   //商品属性(库存商口、劳务/服务)
		double je = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		String strNums = StringUtils.nullToStr(map.get("nums"));
		double cb = map.get("hjcb")==null?0:((Double)map.get("hjcb")).doubleValue(); //成本
		double bhsje = map.get("bhsje")==null?0:((Double)map.get("bhsje")).doubleValue(); //不含税金额
		int nums = 0;
		if(!strNums.equals("")){
			nums = new Integer(strNums).intValue();
		}
		
		if(prop.equals("服务/劳务")){ //如果是服务/劳务类商品，成本价为0
			cb = 0;
		}
		double ml = bhsje - cb;
		hj_nums += nums;
		hj_je += je;
		hj_ml += ml;
		hj_cb += cb;
		hj_bhsje += bhsje;
%>
		<TR>
			<TD class=ReportItemXh><%=product_id %>&nbsp;</TD>
			<TD class=ReportItem><a href="javascript:openWin('<%=product_id %>');"><%=StringUtils.nullToStr(map.get("product_name")) %></a>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_xh")) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(bhsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(cb,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ml,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney ><%=JMath.percent(ml,bhsje) %>&nbsp;</TD>
		</TR>
	
<%
	}
}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合计：</TD>
			<TD class=ReportItem>&nbsp;</TD>		
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
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
			<td width="70%" height="30">注：毛利 = 不含税金额 - 成本；毛利率 = 毛利 / 不含税金额 * 100%；点击商品名称可查看原始单据列表。</td>
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