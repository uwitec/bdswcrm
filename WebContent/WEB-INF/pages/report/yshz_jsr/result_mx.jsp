<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List xsdList = (List)VS.findValue("xsdList");
Map qcMap = (Map)VS.findValue("qcMap");

String start_date = StringUtils.nullToStr(VS.findValue("start_date"));  //开始时间
String end_date = StringUtils.nullToStr(VS.findValue("end_date"));      //结束时间
String jsr = StringUtils.nullToStr(VS.findValue("jsr"));        //销售人员
String client_name = StringUtils.nullToStr(VS.findValue("client_name"));        //客户编号

String strCon = "";

strCon = "日期：" + start_date + "至" + end_date;

if(!jsr.equals("")){
	strCon += "　业务员：" + StaticParamDo.getRealNameById(jsr);
}
if(!client_name.equals("")){
	strCon += "　客户名称：" + StaticParamDo.getClientNameById(client_name);
}
%>

<html>
<head>
<title>业务员应收汇总--明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript">
	function openWin(url){
		var fea ='width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>业务员应收汇总--明细</B></font><br><%=strCon %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>日期</TD>
			<TD class=ReportHead>到期日期</TD>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>客户名称</TD>	
			<TD class=ReportHead>联系人</TD>	
			<TD class=ReportHead>电话</TD>	
			<TD class=ReportHead>发生金额</TD>
			<TD class=ReportHead>已收金额</TD>
			<TD class=ReportHead>应收金额</TD>
			<TD class=ReportHead>应收小计</TD>
		</TR>
	</THEAD>
<% 
double qcje =  qcMap.get(jsr)==null?0:((Double)qcMap.get(jsr)).doubleValue();
%>	
	<TBODY>
		<TR>
			<TD class=ReportItemXh><B>期初应收</B></TD>
			<TD class=ReportItemXh>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(qcje,2) %>&nbsp;</TD>
		</TR>	
<%
double xj_ys = qcje;
if(xsdList != null && xsdList.size()>0){
	
	double hj_ysje = 0;
	double hj_xsdje = 0;
	double hj_skje = 0;
	
	
	for(int i=0;i<xsdList.size();i++){
		Map map = (Map)xsdList.get(i);
		
		double xsdje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
		double skje = map.get("skje")==null?0:((Double)map.get("skje")).doubleValue();
		
		double ysje = xsdje - skje;
		
		hj_ysje += ysje;
		hj_xsdje += xsdje;
		
		xj_ys += ysje;
%>	
		<TR>
			<TD class=ReportItemXh><%=StringUtils.nullToStr(map.get("creatdate")) %>&nbsp;</TD>
			<TD class=ReportItemXh><%=StringUtils.nullToStr(map.get("ysrq")) %>&nbsp;</TD>
			<TD class=ReportItemXh><a href="javascript:openWin('viewXsd.html?id=<%=StringUtils.nullToStr(map.get("id")) %>');"><%=StringUtils.nullToStr(map.get("id")) %></a>&nbsp;</TD>
			<TD class=ReportItem><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("kh_lxr")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("kh_lxdh")) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(xsdje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(skje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ysje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(xj_ys,2) %>&nbsp;</TD>
		</TR>
<%
	}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合 计</TD>
			<TD class=ReportItemXh>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_xsdje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_skje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_ysje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(xj_ys,2) %>&nbsp;</TD>
		</TR>
<%
}
%>
	</TBODY>
</TABLE>
<BR>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>