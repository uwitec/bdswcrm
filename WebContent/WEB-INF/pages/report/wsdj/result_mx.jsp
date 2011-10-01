<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XsmxReportService xsmxReportService = (XsmxReportService)VS.findValue("xsmxReportService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));  //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));      //结束时间
String dept_id = StringUtils.nullToStr(request.getParameter("dept_id"));        //部门
String xsry_id = StringUtils.nullToStr(request.getParameter("xsry_id"));        //销售人员
String client_name = StringUtils.nullToStr(request.getParameter("client_id"));        //客户编号
String khjl = StringUtils.nullToStr(request.getParameter("khjl"));        //销售人员
String strCon = "";

strCon = "日期：" + start_date + "至" + end_date;

if(!dept_id.equals("")){
	strCon += "&nbsp;&nbsp;部门：" + StaticParamDo.getDeptNameById(dept_id);
}

if(!khjl.equals("")){
	strCon += "&nbsp;&nbsp;客户经理：" + khjl;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售未收单据汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function openWin(url){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>销售未收单据汇总</B></font><br><%=strCon %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead width="70">日期</TD>
			<TD class=ReportHead width="70">到期日期</TD>
			<TD class=ReportHead width="100">单据号</TD>
			<TD class=ReportHead>客户名称</TD>	
			<TD class=ReportHead width="50">联系人</TD>	
			<TD class=ReportHead>电话</TD>	
			<TD class=ReportHead width="50">销售员</TD>
			
			<TD class=ReportHead>商品名称</TD>
			<TD class=ReportHead>商品规格</TD>
			<TD class=ReportHead width="50">单价</TD>
			<TD class=ReportHead>数量</TD>
			
			<TD class=ReportHead width="60">发生金额</TD>
			<TD class=ReportHead width="60">应收金额</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%
List results = xsmxReportService.getWsdjKhjlList(start_date,end_date,dept_id,xsry_id,khjl,client_name,"");
if(results != null && results.size()>0){
	
	double hj_je = 0;
	double hj_xsdje = 0;
	
	for(int i=0;i<results.size();i++){
		Map map = (Map)results.get(i);
		
		double xsdje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
		double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();
		String xsd_id = StringUtils.nullToStr(map.get("id"));
		
		hj_je += je;
		hj_xsdje += xsdje;
%>	
		<TR>
			<TD class=ReportItem width="70"><%=StringUtils.nullToStr(map.get("creatdate")) %>&nbsp;</TD>
			<TD class=ReportItem width="70"><%=StringUtils.nullToStr(map.get("ysrq")) %>&nbsp;</TD>
			<TD class=ReportItem width="100"><a href="javascript:openWin('viewXsd.html?id=<%=xsd_id %>');"><%=xsd_id %></a>&nbsp;</TD>
			<TD class=ReportItem><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("kh_lxr")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("kh_lxdh")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("fzr"))) %>&nbsp;</TD>
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem width="50">&nbsp;</TD>
			<TD class=ReportItem width="20">&nbsp;</TD>
			
			<TD class=ReportItemMoney><%=JMath.round(xsdje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		</TR>
<%
		List xsdProducts = xsmxReportService.getXsdMxList(xsd_id);
		
		if(xsdProducts != null && xsdProducts.size() > 0){
			for(int k=0;k<xsdProducts.size();k++){
				Map mxMap = (Map)xsdProducts.get(k);
				
				double price = mxMap.get("price")==null?0:((Double)mxMap.get("price")).doubleValue();
				double jgtz = mxMap.get("jgtz")==null?0:((Double)mxMap.get("jgtz")).doubleValue();
				double xj = mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue();
%>
		<TR>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			
			<TD class=ReportItem><%=StringUtils.nullToStr(mxMap.get("product_name")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(mxMap.get("product_xh")) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(price+jgtz,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=StringUtils.nullToStr(mxMap.get("nums")) %>&nbsp;</TD>			
			<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
			
			<TD class=ReportItemMoney>&nbsp;</TD>
		</TR>
<%
			}
		}
	}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合 计</TD>
			<TD class=ReportItemXh>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>			
			
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_xsdje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
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
</div>
</body>
</html>