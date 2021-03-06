<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

ClientWlStatService clientWlStatService = (ClientWlStatService)VS.findValue("clientWlStatService");
List clientList = (List)VS.findValue("clientList");

String q_client_name = StringUtils.nullToStr(request.getParameter("client_name"));   //开始时间

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));   //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));       //结束时间
String flag = StringUtils.nullToStr(request.getParameter("flag"));               //不显示0往来单位
String flag2 = StringUtils.nullToStr(request.getParameter("flag2"));             //不显示余额为0往来单位

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商应付汇总表</title>
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
	function openWin(client_name){
		location.href = "getYfDzdResult.html?start_date=<%=start_date%>&end_date=<%=end_date%>&client_name=" + client_name;
	}	
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>供应商应付汇总表</B></font><br>日期：<%=start_date %> 至 <%=end_date %></TD>
		</TR>
	</TBODY>
</TABLE>
<form name="myform" method="post" action="getYfDzdResult.html">
	<input type="hidden" name="start_date" value="<%=start_date %>">
	<input type="hidden" name="end_date" value="<%=end_date %>">
	<input type="hidden" name="client_name" value="">
</form>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead width="18%">供应商名称</TD>
			<TD class=ReportHead width="10%">客户经理</TD>
			<TD class=ReportHead width="8%">期初数</TD>
			<TD class=ReportHead width="8%">本期发生数</TD>
			<TD class=ReportHead width="8%">本期付款</TD>
			<TD class=ReportHead width="8%">当前应付款</TD>
			<TD class=ReportHead width="8%">未到期应付款</TD>
			<TD class=ReportHead width="8%">超期应付款</TD>
			<TD class=ReportHead width="8%">其它应付</TD>
			<TD class=ReportHead width="8%">预付款</TD>
			<TD class=ReportHead width="8%">最长超期天数</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%

double hj_qcs = 0;
double hj_bqfs = 0;
double hj_ysje = 0;
double hj_dqys = 0;

double hj_wdqyfk = 0;
double hj_cqyfk = 0;
double hj_yfk = 0;
double hj_qtyfk = 0;

Map qcMap = clientWlStatService.getClientQc(start_date);

Map infoMap = clientWlStatService.getClientWlInfo(start_date,end_date,q_client_name);

Map maxCqtsMap = clientWlStatService.getClientYfMaxCqts(q_client_name);  //最长超期天数
Map wdqyfkMap = clientWlStatService.getClientWdqyfk(q_client_name);    //未到期应付款
Map cqyfkMap = clientWlStatService.getClientCqyfk(q_client_name);      //超期应付款 

Map yfkMap = clientWlStatService.getClientYufuk(q_client_name);      //预付款
Map qcjsMap = clientWlStatService.getClientQcyfjsye(q_client_name);      //期初结算
Map tzjsMap = clientWlStatService.getClientTzyfjsye(q_client_name);      //调账（应收）结算

if(clientList != null && clientList.size() > 0){
	for(int i=0;i<clientList.size();i++){
		Map map = (Map)clientList.get(i);
		
		String client_id = StringUtils.nullToStr(map.get("id"));
		String client_name = StringUtils.nullToStr(map.get("name"));
		String khjl = StringUtils.nullToStr(map.get("khjl"));
		
		double qcs = qcMap.get(client_id+"应付")==null?0:((Double)qcMap.get(client_id+"应付")).doubleValue();  //期初数		
		double bqfs = infoMap.get(client_id+"应付发生")==null?0:((Double)infoMap.get(client_id+"应付发生")).doubleValue();  //本期发生
		double yfje = infoMap.get(client_id+"已付发生")==null?0:((Double)infoMap.get(client_id+"已付发生")).doubleValue();  //本期已收

		double dqys = qcs + bqfs - yfje;  //当前应收=期初数+本期发生-本期已付
		
		String strCqts = StringUtils.nullToStr(maxCqtsMap.get(client_id));
		int cqts = 0; //最长超期天数
		if(!strCqts.equals("")){
			cqts = new Integer(strCqts).intValue();
		}
		double wdqyfk = wdqyfkMap.get(client_id)==null?0:((Double)wdqyfkMap.get(client_id)).doubleValue();  //未到期应付款 
		double cqyfk = cqyfkMap.get(client_id)==null?0:((Double)cqyfkMap.get(client_id)).doubleValue();  //超期应付款
		
		double yfk = yfkMap.get(client_id)==null?0:((Double)yfkMap.get(client_id)).doubleValue();  //预付款
		double qtyfk = (qcjsMap.get(client_id)==null?0:((Double)qcjsMap.get(client_id)).doubleValue()) + (tzjsMap.get(client_id)==null?0:((Double)tzjsMap.get(client_id)).doubleValue()); //其它应付款 

		
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
			hj_wdqyfk += wdqyfk;
			hj_cqyfk += cqyfk;
			hj_yfk += yfk;
			hj_qtyfk += qtyfk;
%>	
		<TR>
			<TD class=ReportItem><a href="#" onclick="openWin('<%=client_id %>');"><%=client_name %></a>&nbsp;</TD>
			<TD class=ReportItemXH><%=khjl %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="期初数"><%=JMath.round(qcs,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="本期发生数"><%=JMath.round(bqfs,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="本期付款"><%=JMath.round(yfje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="当前应付款"><%=JMath.round(dqys,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="未到期应付款"><%=JMath.round(wdqyfk,2) %></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="超期应付款"><font color="red"><%=JMath.round(cqyfk,2) %></font></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="其它应付"><%=JMath.round(qtyfk,2) %></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="预付款"><%=JMath.round(yfk,2) %></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="最长超期天数"><%=cqts %></TD>
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
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_wdqyfk,2) %></TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><font color="red"><%=JMath.round(hj_cqyfk,2) %></font></TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_qtyfk,2) %></TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_yfk,2) %></TD>
		<TD class=ReportItemMoney style="font-weight:bold">--</TD>	
	</TR>	
	</TBODY>
	
</TABLE>
<br>
<table width="99%">
	<tr>
		<td width="70%" height="30">&nbsp;说明：点击客户编号或客户名称打开应付对账单</td>
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