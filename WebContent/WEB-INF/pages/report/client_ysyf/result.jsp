<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

ClientsService clientsService = (ClientsService)VS.findValue("clientsService");
List clientList = (List)VS.findValue("clientList");
String client_type = StringUtils.nullToStr(request.getParameter("client_type"));   //客户类型
String q_client_name = StringUtils.nullToStr(request.getParameter("client_name"));   //客户名称
String khjl = StringUtils.nullToStr(request.getParameter("khjl"));               //客户经理
String flag = StringUtils.nullToStr(request.getParameter("flag"));               //应付不为0
String flag2 = StringUtils.nullToStr(request.getParameter("flag2"));             //应收不为0
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>单位应收应付表</title>
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
	
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>单位应收应付表</B></font></TD>
		</TR>
	</TBODY>
</TABLE>
<form>
	<input type="hidden" name="client_name" value="">
</form>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<thead>
	<tr>
		<td class=ReportHead width="12%">客户编号</td>
		<td class=ReportHead width="18%">客户名称</td>
		<td class=ReportHead width="16%">客户类型</td>
		<td class=ReportHead width="8%">限额</td>
		<td class=ReportHead width="8%">应收款</td>
		<td class=ReportHead width="8%">应付款</td>
		<td class=ReportHead width="10%">客户经理</td>
	</tr>
	</thead>
<TBODY>
	<%
    double qcys = 0;
    double bqfs = 0;
    double ysje = 0;
    double qcyf = 0;
    double bqfsyf = 0;
    double yfje = 0;
    
	Map qcMap = (Map)clientsService.getClientYsyfQc(q_client_name,khjl,client_type,DateComFunc.getToday());
	Map wlMap = (Map)clientsService.getClientYsyfWlInfo(q_client_name,khjl,client_type,DateComFunc.getToday());
    if(clientList != null && clientList.size() > 0){
	for(int i=0;i<clientList.size();i++){
		Map map = (Map)clientList.get(i);		
		double xe = map.get("xe")==null?0:((Double)map.get("xe")).doubleValue();
		
		String client_id = StringUtils.nullToStr(map.get("id"));
		
		double curYs = 0l;
		double curYf = 0l;
		
		qcys = qcMap.get(client_id+"应收")==null?0:((Double)qcMap.get(client_id+"应收")).doubleValue();  //昨日期初应收数		
		bqfs = wlMap.get(client_id+"应收发生")==null?0:((Double)wlMap.get(client_id+"应收发生")).doubleValue();  //昨日应收发生
		ysje = wlMap.get(client_id+"已收发生")==null?0:((Double)wlMap.get(client_id+"已收发生")).doubleValue();  //昨日已收发生
			
		qcyf = qcMap.get(client_id+"应付")==null?0:((Double)qcMap.get(client_id+"应付")).doubleValue();  //昨日期初应付数		
		bqfsyf = wlMap.get(client_id+"应付发生")==null?0:((Double)wlMap.get(client_id+"应付发生")).doubleValue();  //本期发生
		yfje = wlMap.get(client_id+"已付发生")==null?0:((Double)wlMap.get(client_id+"已付发生")).doubleValue();  //本期已收
		
			
		//不显示应付为0
		boolean bl = false;		
		if(flag.equals("是")){
			if(qcyf!=0 || bqfsyf!=0 || yfje !=0){
				bl = true;
			}
		}else{
			bl = true;
		}
		
		//不显示应收为0
		boolean bl2 = false;		
		if(flag2.equals("是")){
			if(qcys!=0 || bqfs!=0 || ysje !=0){
				bl2 = true;
			}
		}else{
			bl2 = true;
		}
		
		
		  if(bl && bl2){
			curYs = qcys + bqfs - ysje;  //当前应收数
			curYf = qcyf + bqfsyf - yfje;  //当前应付数
		
	%>
	<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
		<td class=ReportItemXH><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td class=ReportItemXH><%=StringUtils.nullToStr(map.get("name")) %></td>
		<td class=ReportItemXH><%=StringUtils.nullToStr(map.get("client_type")) %></td>
		<td class=ReportItemMoney><%=JMath.round(xe,2) %></td>
		<td class=ReportItemMoney><%=JMath.round(curYs,2) %></td>
		<td class=ReportItemMoney><%=JMath.round(curYf,2) %></td>
		<td class=ReportItemXH><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("khjl"))) %></td>		
	</tr>	
	<%
	}
  }
}
	%>
	</TBODY>
</table>
<br>
<table width="99%">
		<tr>		
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