<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

YfHzMxService yfHzMxService = (YfHzMxService)VS.findValue("yfHzMxService");
List clientList = (List)VS.findValue("clientList");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));   //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));       //结束时间
String flag = StringUtils.nullToStr(request.getParameter("flag"));               //不显示0往来单位
String flag2 = StringUtils.nullToStr(request.getParameter("flag2"));             //不显示余额为0往来单位

%>

<html>
<head>
<title>应付汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(client_name){
		var fea ='width=' + (screen.availWidth-4) + ',height=' + (screen.availHeight-30) + ',left=0,top=0,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		var url = "getYfDzdResult.html?start_date=<%=start_date%>&end_date=<%=end_date%>&client_name=" + client_name;
		window.open(url,"客户销售汇总",fea);	
	}	
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>应付汇总</B></font><br>日期：<%=start_date %> 至 <%=end_date %></TD>
		</TR>
	</TBODY>
</TABLE>
<form name="myform" method="post" action="getYfDzdResult.html">
	<input type="hidden" name="start_date" value="<%=start_date %>">
	<input type="hidden" name="end_date" value="<%=end_date %>">
	<input type="hidden" name="client_name" value="">
</form>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead width="15%">客户编号</TD>
			<TD class=ReportHead width="25%">客户名称</TD>
			<TD class=ReportHead width="15%">期初数</TD>
			<TD class=ReportHead width="15%">本期发生数</TD>
			<TD class=ReportHead width="15%">本期付款</TD>
			<TD class=ReportHead width="15%">当前应付款</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%

double hj_qcs = 0;
double hj_bqfs = 0;
double hj_ysje = 0;
double hj_dqys = 0;

if(clientList != null && clientList.size() > 0){
	for(int i=0;i<clientList.size();i++){
		Map map = (Map)clientList.get(i);
		
		String client_id = StringUtils.nullToStr(map.get("id"));
		String client_name = StringUtils.nullToStr(map.get("name"));
		
		Map qc_map = yfHzMxService.getYfQc(client_id,start_date);  //期初信息		
		double bqfs = yfHzMxService.getBqfs(client_id,start_date,end_date); //本期发生
		double yfje = yfHzMxService.getBqyf(client_id,start_date,end_date); //本期已付
		
		double qcs = 0l;  //期初数
		if(qc_map != null){
			qcs = qc_map.get("yfqc")==null?0:((Double)qc_map.get("yfqc")).doubleValue();
		}

		double dqys = qcs + bqfs - yfje;  //当前应收=期初数+本期发生-本期已付
		
		//不显示0往来单位
		boolean bl = false;		
		if(flag.equals("是")){
			if(bqfs!=0 || yfje !=0){
				bl = true;
			}
		}else{
			bl = true;
		}
		
		//不显示余额为0往来单位
		boolean bl2 = false;		
		if(flag2.equals("是")){
			if(dqys !=0){
				bl2 = true;
			}
		}else{
			bl2 = true;
		}
		
		if(bl && bl2){
			hj_qcs += qcs;
			hj_bqfs += bqfs;
			hj_ysje += yfje;
			hj_dqys += dqys;
			
%>	
		<TR>
	 		<TD class=ReportItem><a href="#" onclick="openWin('<%=client_id %>');"><%=client_id %></a>&nbsp;</TD>
			<TD class=ReportItem><a href="#" onclick="openWin('<%=client_id %>');"><%=client_name %></a>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(qcs,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(bqfs,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(yfje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(dqys,2) %>&nbsp;</TD>
		</TR>

<%		
		}
	}
	
}

%>
	<TR>
		<TD class=ReportItem style="font-weight:bold">合计（金额）</TD>
		<TD class=ReportItem style="font-weight:bold">&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_qcs,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_bqfs,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_ysje,2) %>&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_dqys,2) %>&nbsp;</TD>
	</TR>	
	</TBODY>
	
</TABLE>
<br>
<table width="99%">
	<tr>
		<td width="70%" height="30">&nbsp;说明：客户编号或客户名称打开应付对账单</td>
		<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>