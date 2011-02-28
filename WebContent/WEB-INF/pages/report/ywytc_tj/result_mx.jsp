<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XstjXsryService xstjXsryService = (XstjXsryService)VS.findValue("xstjXsryService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String user_id = StringUtils.nullToStr(request.getParameter("user_id")); 

List results = xstjXsryService.getYwytcMx(start_date,end_date,user_id);

String strCon = "";

strCon = "日期：" + start_date + "至" + end_date;
if(!user_id.equals("")){
	strCon += "&nbsp;&nbsp;销售人员：" + StaticParamDo.getRealNameById(user_id);
}
%>

<html>
<head>
<title>业务员提成明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>业务员提成明细</B></font><br><%=strCon %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead width="70">日期</TD>
			<TD class=ReportHead width="100">单据号</TD>
			<TD class=ReportHead width="50">类型</TD>
			<TD class=ReportHead width="120">客户</TD>
			<TD class=ReportHead>商品名称</TD>
			<TD class=ReportHead>型号</TD>
			<TD class=ReportHead>数量</TD>		
			<TD class=ReportHead>考核毛利</TD>	
			<TD class=ReportHead>基本提成</TD>
			<TD class=ReportHead>比例点杀</TD>
			<TD class=ReportHead>金额点杀</TD>
			<TD class=ReportHead>超限奖励</TD>	
			<TD class=ReportHead>提成合计</TD>
		</TR>
	</THEAD>
	<TBODY>
<%

if(results != null && results.size()>0){
	
	String ywdj_id = "";
	
	int hj_nums = 0;
	double hj_khml = 0;
	double hj_jbtc = 0;
	double hj_blds = 0;
	double hj_jeds = 0;
	double hj_cxjl = 0;
	double hj_total = 0;
	String strYwtypeTemp="";
	
	for(int i=0;i<results.size();i++){
		
		Map map = (Map)results.get(i);
		
		String id = StringUtils.nullToStr(map.get("id"));
		String sfcytc = StringUtils.nullToStr(map.get("sfcytc"));
		
		String strId = "";
		String strDate = "";
		String strYwtype = "";
		String strClientName = "";		
		
		if(!id.equals(ywdj_id)){
			strId = id;
			strDate = StringUtils.nullToStr(map.get("cz_date"));
			strYwtype = StringUtils.nullToStr(map.get("yw_type"));
			strClientName = StaticParamDo.getClientNameById((String)map.get("client_name"));
			strYwtypeTemp=StringUtils.nullToStr(map.get("yw_type"));
		}
		
		ywdj_id = id;
		
		int nums = new Integer(StringUtils.nullToZero(map.get("nums"))).intValue(); //数量
		
		double khml = map.get("khml") == null?0:((Double)map.get("khml")).doubleValue();   //考核毛利
		double jbtc = map.get("jbtc") == null?0:((Double)map.get("jbtc")).doubleValue();
		double blds = map.get("blds") == null?0:((Double)map.get("blds")).doubleValue();
		double jeds = map.get("jeds") == null?0:((Double)map.get("jeds")).doubleValue();
		double cxjl = map.get("cxjl") == null?0:((Double)map.get("cxjl")).doubleValue();
		
		
		//如果是退货单    超限奖励大于时取0，比例点杀大于0时取0
		//其它单据零售单、销售订单，   超限奖励小于0时取0，比例点杀小于0时取0
		
		if(strYwtypeTemp.equals("退货单")){
			if(cxjl > 0) cxjl = 0;
			if(blds > 0) blds = 0;
		}else{
			if(cxjl < 0) cxjl = 0;
			if(blds < 0) blds = 0;
		}
		
		//如果商品不参与提成
		if(sfcytc.equals("0")){
			jbtc = 0;
			cxjl = 0;
		}
		
		
		double total = jbtc + blds + jeds + cxjl;
		
		hj_khml += khml;
		hj_nums += nums;
		hj_jbtc += jbtc;
		hj_blds += blds;
		hj_jeds += jeds;
		hj_cxjl += cxjl;
		hj_total += total;
%>
		<TR>
			<TD class=ReportItemXH><%=strDate %>&nbsp;</TD>
			<TD class=ReportItemXH><%=strId %>&nbsp;</TD>
			<TD class=ReportItemXH><%=strYwtype %>&nbsp;</TD>
			<TD class=ReportItem><%=strClientName %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_name")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_xh")) %>&nbsp;</TD>								
			<TD class=ReportItemXH nowrap><%=nums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(khml,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(jbtc,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(blds,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(jeds,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(cxjl,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(total,2) %>&nbsp;</TD>					
		</TR>
<%								
	}
%>
		<TR>
			<TD class=ReportItemXH><b>合 计</b>&nbsp;</TD>
			<TD class=ReportItemXH>&nbsp;</TD>
			<TD class=ReportItemXH>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>								
			<TD class=ReportItemXH nowrap><%=hj_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_khml,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_jbtc,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_blds,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_jeds,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_cxjl,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_total,2) %>&nbsp;</TD>				
		</TR>
<%
}
%>

	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">&nbsp;</td>
			<td colspan="3" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>