<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XstjClientService xstjClientService = (XstjClientService)VS.findValue("xstjClientService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));   //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));       //截止时间
String xsry_id = StringUtils.nullToStr(request.getParameter("xsry_id"));         //销售人员编号
String dj_id = StringUtils.nullToStr(request.getParameter("dj_id"));             //单据编号
String client_name = StringUtils.nullToStr(request.getParameter("client_name")); //客户编号


%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售统计（按客户）——统计结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
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
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户销售汇总</B></font><br>日期：<%=start_date %> 至 <%=end_date %> 客户名称：<%=StaticParamDo.getClientNameById(client_name) %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>日期</TD>
			<TD class=ReportHead>业务类型</TD>
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
List xsdList = xstjClientService.getXsdList(start_date,end_date,xsry_id,client_name,dj_id);

//取退货单列表数据
List thdList = xstjClientService.getThdList(start_date,end_date,xsry_id,client_name,dj_id);

double hjje = 0;
if(xsdList != null && xsdList.size()>0){
	for(int k=0;k<xsdList.size();k++){
		Map xsdMap = (Map)xsdList.get(k);
		
		
		
		String creatdate = StringUtils.nullToStr(xsdMap.get("creatdate"));
		String xsd_id = StringUtils.nullToStr(xsdMap.get("id"));
		String xsry_name = StaticParamDo.getRealNameById(StringUtils.nullToStr(xsdMap.get("fzr")));

		double xsdje = xsdMap.get("xsdje")==null?0:((Double)xsdMap.get("xsdje")).doubleValue();
		
		hjje += xsdje;	
%>
		<TR>
			<TD class=ReportItem style="font-weight:bold">
				<a style="cursor:hand" onclick="openWin('viewXsd.html?id=<%=xsd_id %>','销售订单');" title="点击查看原始单据"><%=xsd_id %></a>&nbsp;
			</TD>
			<TD class=ReportItem style="font-weight:bold"><%=creatdate %>&nbsp;</TD>
			<TD class=ReportItem style="font-weight:bold">销售订单&nbsp;</TD>
			<TD class=ReportItem style="font-weight:bold"><%=xsry_name %>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(xsdje,2) %>&nbsp;</TD>
		</TR>
		
<%

		List mxList = xstjClientService.getXsdMxList(xsd_id);
		

		if(mxList != null && mxList.size()>0){
			for(int l=0;l<mxList.size();l++){
				Map mxMap = (Map)mxList.get(l);	
				
				String product_name = StringUtils.nullToStr(mxMap.get("product_name"));
				String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
				
				double price = mxMap.get("price")==null?0:((Double)mxMap.get("price")).doubleValue();
				double jgtz = mxMap.get("jgtz")==null?0:((Double)mxMap.get("jgtz")).doubleValue();
				price = price + jgtz;  //实际售价等于 单价+价格调整
				
				int nums = new Integer(StringUtils.nullToStr(mxMap.get("nums"))).intValue();
				double xj = mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue();	
				
%>
				<TR>
					<TD class=ReportItem>&nbsp;</TD>
					<TD class=ReportItem>&nbsp;</TD>
					<TD class=ReportItem>&nbsp;</TD>
					<TD class=ReportItem>&nbsp;</TD>					
					<TD class=ReportItem><%=product_name %>&nbsp;</TD>
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
	
	
	
<%		
if(thdList != null && thdList.size()>0){
	for(int k=0;k<thdList.size();k++){
		Map thdMap = (Map)thdList.get(k);
		
		
		
		String th_date = StringUtils.nullToStr(thdMap.get("th_date"));
		String thd_id = StringUtils.nullToStr(thdMap.get("thd_id"));
		String xsry_name = StaticParamDo.getRealNameById(StringUtils.nullToStr(thdMap.get("th_fzr")));
		double thdje = 0 - (thdMap.get("thdje")==null?0:((Double)thdMap.get("thdje")).doubleValue());
			
		hjje += thdje;
%>
			<TR>
				<TD class=ReportItem style="font-weight:bold"><a style="cursor:hand" onclick="openWin('viewThd.html?thd_id=<%=thd_id %>');" title="点击查看原始单据"><%=thd_id %></a></TD>
				<TD class=ReportItem style="font-weight:bold"><%=th_date %>&nbsp;</TD>
				<TD class=ReportItem style="font-weight:bold">退货单</TD>
				<TD class=ReportItem style="font-weight:bold"><%=xsry_name %></TD>
				<TD class=ReportItem>&nbsp;</TD>
				<TD class=ReportItem>&nbsp;</TD>
				<TD class=ReportItem>&nbsp;</TD>
				<TD class=ReportItem>&nbsp;</TD>
				<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(thdje,2) %>&nbsp;</TD>
			</TR>
		
<%
		List mxList = xstjClientService.getThdMxList(thd_id);
		

		if(mxList != null && mxList.size()>0){
			for(int l=0;l<mxList.size();l++){
				Map mxMap = (Map)mxList.get(l);	
				
				String product_name = StringUtils.nullToStr(mxMap.get("product_name"));
				String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
				double price = mxMap.get("th_price")==null?0:((Double)mxMap.get("th_price")).doubleValue();
				int nums = new Integer(StringUtils.nullToStr(mxMap.get("nums"))).intValue();
				double xj = 0 - (mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue());		
%>
						<TR>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>					
							<TD class=ReportItem><%=product_name %>&nbsp;</TD>
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
	<input type="button" name="button_print" value=" 打印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>