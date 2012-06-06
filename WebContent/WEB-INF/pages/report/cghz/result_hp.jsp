<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

CghzService cghzService = (CghzService)VS.findValue("cghzService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("clientName"));
String clientId = StringUtils.nullToStr(request.getParameter("clientId"));
String productKind = StringUtils.nullToStr(request.getParameter("productKind"));
String kind_name = StringUtils.nullToStr(request.getParameter("kind_name"));
String cgry_id = StringUtils.nullToStr(request.getParameter("cgry_id"));
String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
String product_xh = StringUtils.nullToStr(request.getParameter("product_xh"));

String product_prop=StringUtils.nullToStr(request.getParameter("product_prop"));

String con = "";
con = "日期：" + start_date + "至" + end_date;
if(!clientId.equals("")){
	con += "&nbsp; 客户名称：" + clientId;
}
if(!kind_name.equals("")){
	con += "&nbsp; 商品类别：" + kind_name;
}
if(!product_name.equals("")){
	con += "&nbsp; 商品名称：" + product_name;
}
if(!product_xh.equals("")){
	con += "&nbsp; 商品规格：" + product_xh;
}

if(!product_prop.equals(""))
{
   if(product_prop.equals("1"))
   {
      con += "&nbsp; 商品属性：库存商品" ;
   }
   else if(product_prop.equals("2"))
   {
      con += "&nbsp; 商品属性：服务商品" ;
   }
}

if(!cgry_id.equals("")){
	con += "&nbsp; 采购人员：" + StaticParamDo.getRealNameById(cgry_id);
}


%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>货品采购汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(product_id,start_date,end_date,client_name,cgry_id,product_prop){
		//var fea ='width=' + screen.availWidth + ',height=' + (screen.availHeight-25) + ',left=0,top=0,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		//var url = "getHpcgMxCondition.html?product_id=" + product_id + "&start_date=" + start_date + "&end_date=" + end_date + "&client_name=" + client_name;
		//window.open(url,'详细信息',fea);
		location.href = "getHpcgMxCondition.html?product_id=" + product_id + "&start_date=" + start_date + "&end_date=" + end_date + "&client_name=" + client_name+ "&cgry_id=" + cgry_id+"&product_prop=" +product_prop;
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>货品采购汇总</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center"  class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>商品编码</TD>
			<TD class=ReportHead>商品名称</TD>
			<TD class=ReportHead>商品规格</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>含税金额</TD>		
			<TD class=ReportHead>税额</TD>
			<TD class=ReportHead>不含税金额</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
List list = cghzService.getHpcgList(productKind, start_date, end_date, client_name, product_name, product_xh,cgry_id,product_prop);
if(list != null && list.size()>0){
	
	int hj_nums = 0;
	double hj_je = 0;
	double hj_sje = 0;
	double hj_bhsje = 0;
	
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		String product_id = StringUtils.nullToStr(map.get("product_id"));
		double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();
		double sje = map.get("sje")==null?0:((Double)map.get("sje")).doubleValue();
		double bhsje = map.get("bhsje")==null?0:((Double)map.get("bhsje")).doubleValue();
		String strNums = StringUtils.nullToStr(map.get("nums"));
		
		int nums = 0;
		if(!strNums.equals("")){
			nums = new Integer(strNums).intValue();
		}
		
		hj_nums += nums;
		hj_je += je;
		hj_sje += sje;
		hj_bhsje += bhsje;
%>
		<TR>
			<TD class=ReportItem><%=product_id %>&nbsp;</TD>
			<TD class=ReportItem><a href="#" onclick="openWin('<%=product_id %>','<%=start_date %>','<%=end_date %>','<%=client_name %>','<%=cgry_id %>','<%=product_prop %>');"><%=StringUtils.nullToStr(map.get("product_name")) %></a>&nbsp;</TD>			
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_xh")) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(sje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(bhsje,2) %>&nbsp;</TD>
		</TR>
<%
	}
%>
		<TR>
			<TD class=ReportItem style="font-weight:bold">合计：</TD>
			<TD class=ReportItem>&nbsp;</TD>			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_sje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_bhsje,2) %>&nbsp;</TD>
		</TR>
<%
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