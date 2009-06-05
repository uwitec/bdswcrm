<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
//应付对账单
//应包括以下信息
//进货单、采购退货单、采购付款

OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

YfHzMxService yfHzMxService = (YfHzMxService)VS.findValue("yfHzMxService");

String client_name = StringUtils.nullToStr(request.getParameter("client_name"));  //客户编号
String start_date = StringUtils.nullToStr(request.getParameter("start_date"));   //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));       //结束时间

double qc = 0l;
Map qc_map = yfHzMxService.getYfQc(client_name,start_date);  //期初信息
if(qc_map != null){
	qc = qc_map.get("yfqc")==null?0:((Double)qc_map.get("yfqc")).doubleValue();
}
%>

<html>
<head>
<title>应付对账单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url,winTitle){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,winTitle,fea);	
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px;line-height:30px"><B>应付对账单</B></font><br>
		    	<font style="FONT-SIZE: 12px;line-height:24px">客户编号：<%=client_name %></font>&nbsp;&nbsp;
		    	<font style="FONT-SIZE: 12px;line-height:24px">客户名称：<%=StaticParamDo.getClientNameById(client_name) %></font>&nbsp;&nbsp;
		    	<font style="FONT-SIZE: 12px;line-height:24px">时间：<%=start_date %> 至 <%=end_date %></font>
		    </TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>业务类型</TD>
			<TD class=ReportHead>交易日期</TD>
			<TD class=ReportHead>经手人</TD>
			<TD class=ReportHead>金额</TD>
		</TR>
	</THEAD>
	
	<TBODY>
		<TR>
			<TD class=ReportItem style="font-weight:bold">期初应付</TD>		
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>				
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(qc,2) %>&nbsp;</TD>
		</TR>	
<%

double hj = qc;

//进货单部分
List mxList = yfHzMxService.getYfmxList(client_name,start_date,end_date);

if(mxList != null && mxList.size() > 0){
	for(int i=0;i<mxList.size();i++){
		Map ysMap = (Map)mxList.get(i);
		
		String dj_id = StringUtils.nullToStr(ysMap.get("dj_id"));
		String url = StringUtils.nullToStr(ysMap.get("url"));
		String ywtype = StringUtils.nullToStr(ysMap.get("ywtype"));
		String jsr = StaticParamDo.getRealNameById(StringUtils.nullToStr(ysMap.get("jsr")));
		String creatdate = StringUtils.nullToStr(ysMap.get("creatdate"));
		double je = ysMap.get("je")==null?0:((Double)ysMap.get("je")).doubleValue();
		
		hj += je;

%>	
		<TR>
			<TD class=ReportItem>
				<a  href="#" onclick="openWin('<%=url+dj_id %>','原始单据');" title="点击查看原始单据"><%=dj_id %></a>			
			</TD>
			<TD class=ReportItem><%=ywtype %>&nbsp;</TD>
			<TD class=ReportItem><%=creatdate %>&nbsp;</TD>
			<TD class=ReportItem><%=jsr %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
		</TR>

<%
	}
}
%>

		<TR>
			<TD class=ReportItem style="font-weight:bold">合计应付</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj,2) %>&nbsp;</TD>
		</TR>

	</TBODY>
	
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击产品编号可查看原始单据列表。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 关闭 " onclick="window.close();"> 
</center>
</body>
</html>